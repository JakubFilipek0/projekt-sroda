package com.example.projektsroda.repository;

import com.example.projektsroda.model.Projekt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjektRepository extends JpaRepository<Projekt, Long> {
}
