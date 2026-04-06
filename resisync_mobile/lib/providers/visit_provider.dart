import 'package:flutter/material.dart';
import 'package:dio/dio.dart';
import '../core/api_client.dart';
import '../models/visit.dart';

class VisitProvider extends ChangeNotifier {
  final ApiClient _apiClient = ApiClient();
  bool _isLoading = false;
  List<Visit> _visits = [];

  bool get isLoading => _isLoading;
  List<Visit> get visits => _visits;

  Future<void> fetchVisits() async {
    _isLoading = true;
    notifyListeners();

    try {
      final response = await _apiClient.dio.get('/visitas');
      if (response.statusCode == 200) {
        final List data = response.data;
        _visits = data.map((json) => Visit.fromJson(json)).toList();
      }
    } catch (e) {
      print('Error fetching visits: $e');
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  Future<String?> registrarVisita(Visit visit) async {
    _isLoading = true;
    notifyListeners();

    try {
      final response = await _apiClient.dio.post('/visitas', data: visit.toJson());
      if (response.statusCode == 201 || response.statusCode == 200) {
        await fetchVisits();
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
    return 'Invalid response from server';
  }
}
