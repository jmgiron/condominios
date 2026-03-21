package com.resisync.backend.service;

import com.resisync.backend.model.Notificacion;
import com.resisync.backend.model.Residente;
import com.resisync.backend.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public void notificarResidente(Residente residente, String titulo, String mensaje) {
        Notificacion notificacion = new Notificacion();
        notificacion.setResidente(residente);
        notificacion.setTitulo(titulo);
        notificacion.setMensaje(mensaje);
        
        Notificacion saved = notificacionRepository.save(notificacion);

        // Emitir a los clientes subscritos
        messagingTemplate.convertAndSend("/topic/residentes/" + residente.getId(), saved.getMensaje());
    }
}
