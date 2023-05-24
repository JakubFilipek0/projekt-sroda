package com.example.projektsroda.controller;

import com.example.projektsroda.model.AppUser;
import com.example.projektsroda.model.Projekt;
import com.example.projektsroda.model.Zadanie;
import com.example.projektsroda.repository.AppUserRepository;
import com.example.projektsroda.repository.ProjektRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.util.*;

@RestController
@RequestMapping("/api")
public class ProjektController {

    @Autowired
    ProjektRepository projektRepository;
    @Autowired
    AppUserRepository appUserRepository;

    @GetMapping("/projekt")
    public ResponseEntity<List<Projekt>> getAllProjekt() {
        List<Projekt> projektList = projektRepository.findAll();

        if (projektList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(projektList, HttpStatus.OK);
    }

    @GetMapping("/projekt/{id}")
    public ResponseEntity<Projekt> getProjektById(@PathVariable("id") Long id) {
        Projekt projekt = projektRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projekt with id: " + id + " doesn't exist"));
        return new ResponseEntity<>(projekt, HttpStatus.OK);
    }

    @PostMapping("/projekt")
    public ResponseEntity<Projekt> createProjekt(@RequestBody Projekt projekt) {
        Projekt _projekt = projektRepository.save(projekt);
        return new ResponseEntity<>(_projekt, HttpStatus.CREATED);
    }

    @DeleteMapping("/projekt/{id}")
    public ResponseEntity<HttpStatus> deleteProjektById(@PathVariable("id") Long id) {
        projektRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/projekt/{projektId}/user/{userId}")
    public ResponseEntity<?> addUserToProjekt(@PathVariable("projektId") Long projektId, @PathVariable("userId") Long userId) {
        Projekt projekt = projektRepository.findById(projektId)
                .orElseThrow(() -> new EntityNotFoundException("Projekt with id: " + projektId + "doesn't exist"));
        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + userId + "doesn't exist"));

        projekt.getAppUsers().add(appUser);
        appUser.getProjekts().add(projekt);
        projektRepository.save(projekt);
        appUserRepository.save(appUser);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/addUser")
    public ResponseEntity<AppUser> addUser(@RequestBody AppUser appUser) {
        AppUser _appUser = appUserRepository.save(appUser);
        return new ResponseEntity<>(_appUser, HttpStatus.CREATED);
    }

    @GetMapping("/projekt/{projektId}/user")
    public ResponseEntity<List<AppUser>> getProjektAppUsers(@PathVariable("projektId") Long projektId) {
        Projekt projekt = projektRepository.findById(projektId)
                .orElseThrow(() -> new EntityNotFoundException("Projekt with id: " + projektId + " doesn't exist"));
        Set<AppUser> usersSet = projekt.getAppUsers();
        List<AppUser> usersList = new ArrayList<>(usersSet);
        return new ResponseEntity<>(usersList, HttpStatus.OK);

    }

}
