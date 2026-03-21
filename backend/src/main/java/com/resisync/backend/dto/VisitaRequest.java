package com.resisync.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class VisitaRequest {
    private String nombreVisitante;
    private LocalDateTime fechaLlegada;
    private String whatsapp;
    private Integer duracionEstimada; // En minutos
    private String tipoVisita; // Personal, Delivery, Servicio
}
