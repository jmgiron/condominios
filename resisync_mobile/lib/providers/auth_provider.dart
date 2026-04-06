import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:jwt_decoder/jwt_decoder.dart';
import 'package:dio/dio.dart';
import '../core/api_client.dart';

class AuthProvider extends ChangeNotifier {
  final ApiClient _apiClient = ApiClient();
  bool _isAuthenticated = false;
  bool _isLoading = true;
  String? _userEmail;
  String? _userRole;

  bool get isAuthenticated => _isAuthenticated;
  bool get isLoading => _isLoading;
  String? get userEmail => _userEmail;
  String? get userRole => _userRole;

  AuthProvider() {
    checkAuthStatus();
  }

  Future<void> checkAuthStatus() async {
    final prefs = await SharedPreferences.getInstance();
    final token = prefs.getString('jwt_token');

    if (token != null && !JwtDecoder.isExpired(token)) {
      _isAuthenticated = true;
      Map<String, dynamic> decodedToken = JwtDecoder.decode(token);
      _userEmail = decodedToken['sub'];
      // Se puede expandir para que el Backend retorne una lista de Authorities o la obtengamos de un /me
      _userRole = "RESIDENTE"; 
    } else {
      _isAuthenticated = false;
      await prefs.remove('jwt_token');
    }
    _isLoading = false;
    notifyListeners();
  }

  Future<String?> login(String email, String password) async {
    _isLoading = true;
    notifyListeners();

    try {
      final response = await _apiClient.dio.post('/auth/login', data: {
        'email': email,
        'password': password,
      });

      if (response.statusCode == 200) {
        final token = response.data['accessToken'];
        final prefs = await SharedPreferences.getInstance();
        await prefs.setString('jwt_token', token);
        await checkAuthStatus();
        return null;
      }
    } on DioException catch (e) {
      if (e.response != null) {
        return 'Server Error ${e.response?.statusCode}: ${e.response?.data}';
      }
      return 'Network Error: ${e.message}';
    } catch (e) {
      return 'Unexpected Error: $e';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
    return 'Invalid or empty server response';
  }

  Future<void> logout() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove('jwt_token');
    _isAuthenticated = false;
    _userEmail = null;
    _userRole = null;
    notifyListeners();
  }
}
