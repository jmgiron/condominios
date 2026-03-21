package com.resisync.backend.controller;

import com.resisync.backend.service.AccesoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accesos")
@RequiredArgsConstructor
public class AccesoController {

    private final AccesoService accesoService;

    @PostMapping("/entrada/{token}")
    public ResponseEntity<String> registrarEntrada(Authentication authentication, @PathVariable String token) {
        String porteroEmail = authentication.getName();
        String mensaje = accesoService.registrarEntrada(token, porteroEmail);
        return ResponseEntity.ok(mensaje);
    }
}
