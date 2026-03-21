package com.resisync.backend.dto;

import com.resisync.backend.model.Role;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ResidenteDTO {
    private Long id;
    private String nombre;
    private String email;
    private String casaApto;
    private String telefono;
    private Role rol;
    private LocalDateTime creadoEn;
}
