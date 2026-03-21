import 'package:flutter/material.dart';

class NotificationsScreen extends StatelessWidget {
  const NotificationsScreen({super.key});

  @override
  Widget build(BuildContext context) {
    // Ejemplo de renderizado de notificaciones simulando el payload via WebSocket
    final notifications = [
      {'titulo': 'Tu visitante ha llegado', 'mensaje': 'El visitante Carlos acaba de ingresar a la portería.', 'fecha': 'Hace 5 min'},
      {'titulo': 'Visita programada exitosa', 'mensaje': 'Se generó un QR para la visita de delivery.', 'fecha': 'Hace 2 horas'},
      {'titulo': 'Bienvenido a ResiSync', 'mensaje': 'Nos alegra tenerte aquí.', 'fecha': 'Hace 1 día'},
    ];

    return Scaffold(
      appBar: AppBar(
        title: const Text('Mis Alertas'),
      ),
      body: ListView.separated(
        itemCount: notifications.length,
        separatorBuilder: (_, __) => const Divider(height: 1),
        itemBuilder: (context, index) {
          final n = notifications[index];
          return ListTile(
            contentPadding: const EdgeInsets.all(16),
            leading: const CircleAvatar(
              backgroundColor: Color(0xFFEF4444),
              child: Icon(Icons.notifications, color: Colors.white),
            ),
            title: Text(n['titulo']!, style: const TextStyle(fontWeight: FontWeight.bold)),
            subtitle: Padding(
              padding: const EdgeInsets.only(top: 8.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(n['mensaje']!),
                  const SizedBox(height: 8),
                  Text(n['fecha']!, style: const TextStyle(fontSize: 12, color: Colors.grey)),
                ],
              ),
            ),
          );
        },
      ),
    );
  }
}
