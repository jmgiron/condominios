package com.resisync.backend.service;

import com.resisync.backend.dto.VisitaDTO;
import com.resisync.backend.dto.VisitaRequest;
import com.resisync.backend.model.QrToken;
import com.resisync.backend.model.Residente;
import com.resisync.backend.model.Visita;
import com.resisync.backend.repository.ResidenteRepository;
import com.resisync.backend.repository.VisitaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitaService {

    private final VisitaRepository visitaRepository;
    private final ResidenteRepository residenteRepository;
    private final QrCodeService qrCodeService;
    private final WhatsAppService whatsAppService;

    public VisitaDTO registrarVisita(String residenteEmail, VisitaRequest request) {
        Residente residente = residenteRepository.findByEmail(residenteEmail)
                .orElseThrow(() -> new IllegalArgumentException("Residente no encontrado"));

        Visita visita = new Visita();
        visita.setResidente(residente);
        visita.setNombreVisitante(request.getNombreVisitante());
        visita.setFechaLlegada(request.getFechaLlegada());
        visita.setWhatsapp(request.getWhatsapp());
        visita.setDuracionEstimada(request.getDuracionEstimada());
        visita.setTipoVisita(request.getTipoVisita());
        visita.setEstado("PENDIENTE");

        Visita saved = visitaRepository.save(visita);
        
        // Generar QR
        QrToken qrToken = qrCodeService.generateQrForVisita(saved);
        
        // Simular envío a WhatsApp
        whatsAppService.enviarNotificacionVisita(saved, qrToken);

        return mapToDTO(saved);
    }

    public List<VisitaDTO> getVisitasByResidente(String residenteEmail) {
        Residente residente = residenteRepository.findByEmail(residenteEmail)
                .orElseThrow(() -> new IllegalArgumentException("Residente no encontrado"));
                
        return visitaRepository.findByResidenteId(residente.getId()).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public VisitaDTO mapToDTO(Visita visita) {
        VisitaDTO dto = new VisitaDTO();
        dto.setId(visita.getId());
        dto.setResidenteId(visita.getResidente().getId());
        dto.setNombreVisitante(visita.getNombreVisitante());
        dto.setFechaLlegada(visita.getFechaLlegada());
        dto.setWhatsapp(visita.getWhatsapp());
        dto.setDuracionEstimada(visita.getDuracionEstimada());
        dto.setTipoVisita(visita.getTipoVisita());
        dto.setEstado(visita.getEstado());
        dto.setCreadoEn(visita.getCreadoEn());
        return dto;
    }
}
