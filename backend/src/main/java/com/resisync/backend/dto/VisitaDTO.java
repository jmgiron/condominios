package com.resisync.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class VisitaDTO {
    private Long id;
    private Long residenteId;
    private String nombreVisitante;
    private LocalDateTime fechaLlegada;
    private String whatsapp;
    private Integer duracionEstimada;
    private String tipoVisita;
    private String estado;
    private LocalDateTime creadoEn;
}
