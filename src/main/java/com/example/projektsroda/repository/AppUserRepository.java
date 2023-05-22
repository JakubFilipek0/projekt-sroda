package com.example.projektsroda.repository;

import com.example.projektsroda.model.AppUser;
import com.example.projektsroda.model.Projekt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
}
