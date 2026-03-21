package com.resisync.backend.config;

import com.resisync.backend.model.Residente;
import com.resisync.backend.model.Role;
import com.resisync.backend.repository.ResidenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ResidenteRepository residenteRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String testEmail = "misaelgrande@gmail.com";
        // Revisar si ya existe para no duplicarlo cada vez que inicias el servidor
        
        Optional<Residente> existing = residenteRepository.findByEmail(testEmail);
        if (existing.isEmpty()) {
            Residente admin = new Residente();
            admin.setNombre("Jonathan Giron");
            admin.setEmail(testEmail);
            admin.setPasswordHash(passwordEncoder.encode("Super@2305"));
            admin.setCasaApto("119D");
            admin.setTelefono("61489595");
            admin.setRol(Role.RESIDENTE);

            residenteRepository.save(admin);
            System.out.println("=========================================");
            System.out.println("Usuario de prueba '" + testEmail + "' creado exitosamente!");
            System.out.println("=========================================");
        }
    }
}
