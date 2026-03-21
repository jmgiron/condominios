import 'package:dio/dio.dart';
import 'package:shared_preferences/shared_preferences.dart';

class ApiClient {
  // Alias de localhost para simulador Android, cámbialo a tu IP local si lo pruebas en un teléfono real
  static const String baseUrl = 'http://10.0.2.2:8081/api'; 
  final Dio dio;

  ApiClient() : dio = Dio(BaseOptions(baseUrl: baseUrl)) {
    dio.interceptors.add(InterceptorsWrapper(
      onRequest: (options, handler) async {
        final prefs = await SharedPreferences.getInstance();
        final token = prefs.getString('jwt_token');
        if (token != null) {
          options.headers['Authorization'] = 'Bearer $token';
        }
        return handler.next(options);
      },
      onError: (DioException e, handler) {
        // Podríamos manejar errores globales de red o tokens expirados (401)
        return handler.next(e);
      },
    ));
  }
}
