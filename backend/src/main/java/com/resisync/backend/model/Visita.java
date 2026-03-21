package com.resisync.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "visitas")
public class Visita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "residente_id", nullable = false)
    private Residente residente;

    @Column(name = "nombre_visitante", nullable = false)
    private String nombreVisitante;

    @Column(name = "fecha_llegada", nullable = false)
    private LocalDateTime fechaLlegada;

    @Column(nullable = false)
    private String whatsapp;

    @Column(name = "duracion_estimada")
    private Integer duracionEstimada; // En minutos

    @Column(name = "tipo_visita")
    private String tipoVisita; // Personal, Delivery, Servicio Tecnico

    private String estado; // PENDIENTE, APROBADA, RECHAZADA, FINALIZADA

    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    @PrePersist
    protected void onCreate() {
        creadoEn = LocalDateTime.now();
        if (estado == null) {
            estado = "PENDIENTE";
        }
    }
}
