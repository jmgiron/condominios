package com.resisync.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "accesos")
public class Acceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visita_id", nullable = false)
    private Visita visita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portero_id", nullable = false)
    private Residente portero;

    @Column(name = "fecha_entrada")
    private LocalDateTime fechaEntrada;

    @Column(name = "fecha_salida")
    private LocalDateTime fechaSalida;

    private String estado; // ENTRADA, SALIDA

    @PrePersist
    protected void onCreate() {
        if (fechaEntrada == null) {
            fechaEntrada = LocalDateTime.now();
        }
    }
}
