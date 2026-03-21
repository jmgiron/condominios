package com.resisync.backend.repository;

import com.resisync.backend.model.Residente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ResidenteRepository extends JpaRepository<Residente, Long> {
    Optional<Residente> findByEmail(String email);
}
