package com.resisync.backend.controller;

import com.resisync.backend.dto.ResidenteDTO;
import com.resisync.backend.model.Residente;
import com.resisync.backend.service.ResidenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/residentes")
@RequiredArgsConstructor
public class ResidenteController {

    private final ResidenteService residenteService;

    @PostMapping
    public ResponseEntity<ResidenteDTO> createResidente(@RequestBody Residente residente) {
        ResidenteDTO created = residenteService.createResidente(residente);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ResidenteDTO>> getAllResidentes() {
        return ResponseEntity.ok(residenteService.getAllResidentes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResidenteDTO> getResidenteById(@PathVariable Long id) {
        return ResponseEntity.ok(residenteService.getResidenteById(id));
    }
}
