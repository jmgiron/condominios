package com.resisync.backend.service;

import com.resisync.backend.model.Acceso;
import com.resisync.backend.model.QrToken;
import com.resisync.backend.model.Residente;
import com.resisync.backend.model.Role;
import com.resisync.backend.repository.AccesoRepository;
import com.resisync.backend.repository.QrTokenRepository;
import com.resisync.backend.repository.ResidenteRepository;
import com.resisync.backend.repository.VisitaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccesoService {

    private final AccesoRepository accesoRepository;
    private final QrTokenRepository qrTokenRepository;
    private final ResidenteRepository residenteRepository;
    private final VisitaRepository visitaRepository;
    private final NotificacionService notificacionService;

    public String registrarEntrada(String tokenStr, String porteroEmail) {
        Residente portero = residenteRepository.findByEmail(porteroEmail)
                .orElseThrow(() -> new IllegalArgumentException("Portero no encontrado"));

        if (portero.getRol() != Role.PORTERO && portero.getRol() != Role.ADMIN) {
            throw new IllegalArgumentException("Usuario no tiene permisos de portería");
        }

        QrToken qrToken = qrTokenRepository.findByToken(tokenStr)
                .orElseThrow(() -> new IllegalArgumentException("Token QR inválido"));

        if (qrToken.isUsado()) {
            throw new IllegalArgumentException("Este código QR ya fue utilizado");
        }

        if (LocalDateTime.now().isAfter(qrToken.getFechaExpiracion())) {
            throw new IllegalArgumentException("El código QR ha expirado");
        }

        Acceso acceso = new Acceso();
        acceso.setVisita(qrToken.getVisita());
        acceso.setPortero(portero);
        acceso.setEstado("ENTRADA");
        accesoRepository.save(acceso);

        qrToken.setUsado(true);
        qrTokenRepository.save(qrToken);

        qrToken.getVisita().setEstado("ACTIVA");
        visitaRepository.save(qrToken.getVisita());
        
        String nombreVisitante = qrToken.getVisita().getNombreVisitante();
        
        // Disparar WebSocket al residente asociado
        notificacionService.notificarResidente(qrToken.getVisita().getResidente(), 
                "Tu visitante ha llegado", 
                "El visitante " + nombreVisitante + " acaba de ingresar a la portería.");

        return "Acceso concedido para la visita de: " + nombreVisitante;
    }
}
