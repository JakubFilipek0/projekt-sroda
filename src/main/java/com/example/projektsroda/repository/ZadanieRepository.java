package com.example.projektsroda.repository;

import com.example.projektsroda.model.Zadanie;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ZadanieRepository extends JpaRepository<Zadanie, Long> {
//    List<Zadanie> findByProjektId(Long projektId);

    @Query(value = "select p from Zadanie p where p.projekt.id = :id")
    List<Zadanie> findByProjektId(@Param("id") Long projektId);

    @Transactional
    void deleteByProjektId(Long projektId);
}
