package com.resisync.backend.repository;

import com.resisync.backend.model.Visita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitaRepository extends JpaRepository<Visita, Long> {
    List<Visita> findByResidenteId(Long residenteId);
}
