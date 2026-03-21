package com.resisync.backend.service;

import com.resisync.backend.dto.ResidenteDTO;
import com.resisync.backend.model.Residente;
import com.resisync.backend.model.Role;
import com.resisync.backend.repository.ResidenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResidenteService {

    private final ResidenteRepository residenteRepository;
    private final PasswordEncoder passwordEncoder;

    public ResidenteDTO createResidente(Residente residente) {
        if (residenteRepository.findByEmail(residente.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El email ya está en uso");
        }
        residente.setPasswordHash(passwordEncoder.encode(residente.getPasswordHash()));
        
        if (residente.getRol() == null) {
            residente.setRol(Role.RESIDENTE);
        }

        Residente saved = residenteRepository.save(residente);
        return mapToDTO(saved);
    }

    public List<ResidenteDTO> getAllResidentes() {
        return residenteRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ResidenteDTO getResidenteById(Long id) {
        Residente residente = residenteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Residente no encontrado"));
        return mapToDTO(residente);
    }

    public ResidenteDTO mapToDTO(Residente residente) {
        ResidenteDTO dto = new ResidenteDTO();
        dto.setId(residente.getId());
        dto.setNombre(residente.getNombre());
        dto.setEmail(residente.getEmail());
        dto.setCasaApto(residente.getCasaApto());
        dto.setTelefono(residente.getTelefono());
        dto.setRol(residente.getRol());
        dto.setCreadoEn(residente.getCreadoEn());
        return dto;
    }
}
