package com.resisync.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "qr_tokens")
public class QrToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visita_id", nullable = false)
    private Visita visita;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(columnDefinition="TEXT", name = "qr_imagen_base64")
    private String qrImagenBase64; // Guardar imagen directamente por simplicidad, opcionalmente podrías devolver solo el token

    @Column(name = "fecha_expiracion", nullable = false)
    private LocalDateTime fechaExpiracion;

    private boolean usado = false;

    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    @PrePersist
    protected void onCreate() {
        creadoEn = LocalDateTime.now();
    }
}
