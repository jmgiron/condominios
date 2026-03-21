package com.resisync.backend.controller;

import com.resisync.backend.dto.VisitaDTO;
import com.resisync.backend.dto.VisitaRequest;
import com.resisync.backend.service.VisitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visitas")
@RequiredArgsConstructor
public class VisitaController {

    private final VisitaService visitaService;

    @PostMapping
    public ResponseEntity<VisitaDTO> registrarVisita(Authentication authentication, @RequestBody VisitaRequest request) {
        // En un caso real el Authentication name es el email que se usa en el CustomUserDetailsService
        String email = authentication.getName();
        VisitaDTO created = visitaService.registrarVisita(email, request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VisitaDTO>> misVisitas(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(visitaService.getVisitasByResidente(email));
    }
}
