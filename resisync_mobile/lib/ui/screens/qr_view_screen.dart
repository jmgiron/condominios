import 'package:flutter/material.dart';
import 'package:qr_flutter/qr_flutter.dart';

class QrViewScreen extends StatelessWidget {
  final String qrToken;
  final String visitorName;
  final DateTime expirationDate;

  const QrViewScreen({
    super.key,
    required this.qrToken,
    required this.visitorName,
    required this.expirationDate,
  });

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Código de Acceso')),
      body: Center(
        child: Padding(
          padding: const EdgeInsets.all(24.0),
          child: Card(
            child: Padding(
              padding: const EdgeInsets.all(32.0),
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  Text(
                    'Visita: $visitorName',
                    style: Theme.of(context).textTheme.titleLarge?.copyWith(
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  const SizedBox(height: 24),
                  Container(
                    padding: const EdgeInsets.all(16),
                    decoration: BoxDecoration(
                      color: Colors.white,
                      borderRadius: BorderRadius.circular(16),
                      boxShadow: [
                        BoxShadow(
                          color: Colors.black.withOpacity(0.05),
                          blurRadius: 10,
                          offset: const Offset(0, 4),
                        ),
                      ],
                    ),
                    child: QrImageView(
                      data: qrToken,
                      version: QrVersions.auto,
                      size: 200.0,
                      backgroundColor: Colors.white,
                    ),
                  ),
                  const SizedBox(height: 24),
                  const Text(
                    'Válido hasta:',
                    style: TextStyle(color: Colors.grey),
                  ),
                  Text(
                    '${expirationDate.day}/${expirationDate.month}/${expirationDate.year} ${expirationDate.hour}:${expirationDate.minute.toString().padLeft(2, '0')}',
                    style: const TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.w600,
                      color: Colors.redAccent,
                    ),
                  ),
                  const SizedBox(height: 16),
                  const Text(
                    'Envía o muestra este código al llegar a portería.',
                    textAlign: TextAlign.center,
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}
