class Visit {
  final int? id;
  final int? residenteId;
  final String nombreVisitante;
  final DateTime fechaLlegada;
  final String whatsapp;
  final int duracionEstimada;
  final String tipoVisita;
  final String? estado;
  final DateTime? creadoEn;

  Visit({
    this.id,
    this.residenteId,
    required this.nombreVisitante,
    required this.fechaLlegada,
    required this.whatsapp,
    required this.duracionEstimada,
    required this.tipoVisita,
    this.estado,
    this.creadoEn,
  });

  factory Visit.fromJson(Map<String, dynamic> json) {
    return Visit(
      id: json['id'],
      residenteId: json['residenteId'],
      nombreVisitante: json['nombreVisitante'] ?? '',
      fechaLlegada: DateTime.parse(json['fechaLlegada']),
      whatsapp: json['whatsapp'] ?? '',
      duracionEstimada: json['duracionEstimada'] ?? 60,
      tipoVisita: json['tipoVisita'] ?? 'Personal',
      estado: json['estado'],
      creadoEn: json['creadoEn'] != null ? DateTime.parse(json['creadoEn']) : null,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'nombreVisitante': nombreVisitante,
      'fechaLlegada': fechaLlegada.toIso8601String(),
      'whatsapp': whatsapp,
      'duracionEstimada': duracionEstimada,
      'tipoVisita': tipoVisita,
    };
  }
}
