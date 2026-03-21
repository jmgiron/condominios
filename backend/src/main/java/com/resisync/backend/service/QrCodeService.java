package com.resisync.backend.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.resisync.backend.model.QrToken;
import com.resisync.backend.model.Visita;
import com.resisync.backend.repository.QrTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QrCodeService {

    private final QrTokenRepository qrTokenRepository;

    public QrToken generateQrForVisita(Visita visita) {
        String tokenData = UUID.randomUUID().toString();
        String qrBase64 = null;

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(tokenData, BarcodeFormat.QR_CODE, 300, 300);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] pngData = pngOutputStream.toByteArray();
            qrBase64 = Base64.getEncoder().encodeToString(pngData);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }

        QrToken qrToken = new QrToken();
        qrToken.setVisita(visita);
        qrToken.setToken(tokenData);
        qrToken.setQrImagenBase64(qrBase64);
        
        int estimacion = visita.getDuracionEstimada() != null ? visita.getDuracionEstimada() : 120;
        qrToken.setFechaExpiracion(visita.getFechaLlegada().plusMinutes(estimacion).plusHours(2));

        return qrTokenRepository.save(qrToken);
    }
}
