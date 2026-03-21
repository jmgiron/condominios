package com.resisync.backend.service;

import com.resisync.backend.model.Visita;
import com.resisync.backend.model.QrToken;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {

    public void enviarNotificacionVisita(Visita visita, QrToken qrToken) {
        System.out.println("==============================================");
        System.out.println("Enviando WhatsApp a: " + visita.getWhatsapp());
        System.out.println("Hola " + visita.getNombreVisitante() + "!");
        System.out.println("Invitación de: " + visita.getResidente().getNombre() + " (Apto: " + visita.getResidente().getCasaApto() + ")");
        System.out.println("Por favor muestra este código al llegar a portería.");
        System.out.println("Token QR: " + qrToken.getToken());
        // System.out.println("QR Image (Base64): " + qrToken.getQrImagenBase64());
        System.out.println("Válido hasta: " + qrToken.getFechaExpiracion());
        System.out.println("==============================================");
    }
}
