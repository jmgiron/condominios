import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../providers/auth_provider.dart';
import 'visit_form_screen.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final auth = context.read<AuthProvider>();

    return Scaffold(
      appBar: AppBar(
        title: const Text('Dashboard'),
        actions: [
          IconButton(
            icon: const Icon(Icons.logout),
            onPressed: () => auth.logout(),
          ),
        ],
      ),
      body: ListView(
        padding: const EdgeInsets.all(16),
        children: [
          Card(
            child: ListTile(
              leading: const CircleAvatar(
                backgroundColor: Color(0xFF4F46E5),
                child: Icon(Icons.person, color: Colors.white),
              ),
              title: Text(auth.userEmail ?? 'Usuario'),
              subtitle: Text(auth.userRole ?? 'Residente'),
            ),
          ),
          const SizedBox(height: 24),
          const Text(
            'Accesos Rápidos',
            style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 16),
          GridView.count(
            shrinkWrap: true,
            physics: const NeverScrollableScrollPhysics(),
            crossAxisCount: 2,
            crossAxisSpacing: 16,
            mainAxisSpacing: 16,
            children: [
              _DashboardActionCard(
                icon: Icons.person_add_alt_1,
                title: 'Registrar Visita',
                color: const Color(0xFF4F46E5),
                onTap: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(builder: (_) => const VisitFormScreen()),
                  );
                },
              ),
              _DashboardActionCard(
                icon: Icons.history,
                title: 'Historial',
                color: const Color(0xFF10B981),
                onTap: () {
                  // TODO: Historial UI
                },
              ),
              _DashboardActionCard(
                icon: Icons.qr_code_scanner,
                title: 'Leer Código (Portería)',
                color: const Color(0xFFF59E0B),
                onTap: () {
                  // TODO: Pantalla de MobileScanner para Portero
                },
              ),
              _DashboardActionCard(
                icon: Icons.notifications_active,
                title: 'Alertas',
                color: const Color(0xFFEF4444),
                onTap: () {
                  // TODO: Pantalla de notificaciones
                },
              ),
            ],
          ),
        ],
      ),
    );
  }
}

class _DashboardActionCard extends StatelessWidget {
  final IconData icon;
  final String title;
  final Color color;
  final VoidCallback onTap;

  const _DashboardActionCard({
    required this.icon,
    required this.title,
    required this.color,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: onTap,
      borderRadius: BorderRadius.circular(16),
      child: Card(
        elevation: 0,
        color: color.withOpacity(0.1),
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(16),
          side: BorderSide(color: color.withOpacity(0.3)),
        ),
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Icon(icon, size: 40, color: color),
              const SizedBox(height: 12),
              Text(
                title,
                textAlign: TextAlign.center,
                style: TextStyle(
                  fontSize: 15,
                  fontWeight: FontWeight.w600,
                  color: color,
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
