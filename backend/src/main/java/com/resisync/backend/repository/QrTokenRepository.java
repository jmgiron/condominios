package com.resisync.backend.repository;

import com.resisync.backend.model.QrToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QrTokenRepository extends JpaRepository<QrToken, Long> {
    Optional<QrToken> findByToken(String token);
    Optional<QrToken> findByVisitaId(Long visitaId);
}
