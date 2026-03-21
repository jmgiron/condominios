package com.resisync.backend.security;

import com.resisync.backend.model.Residente;
import com.resisync.backend.repository.ResidenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ResidenteRepository residenteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Residente residente = residenteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Residente no encontrado con email: " + email));

        return new User(residente.getEmail(), residente.getPasswordHash(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + residente.getRol().name())));
    }
}
