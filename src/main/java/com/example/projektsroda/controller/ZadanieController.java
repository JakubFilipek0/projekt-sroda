package com.example.projektsroda.controller;

import com.example.projektsroda.model.Zadanie;
import com.example.projektsroda.repository.ProjektRepository;
import com.example.projektsroda.repository.ZadanieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ZadanieController {

    @Autowired
    private ZadanieRepository zadanieRepository;

    @Autowired
    private ProjektRepository projektRepository;

    @GetMapping("/projekt/{projektId}/zadanie")
    public ResponseEntity<List<Zadanie>> getAllZadanieByProjektId(@PathVariable("projektId") Long projektId) {
        if (!projektRepository.existsById(projektId)) {
            throw new EntityNotFoundException("Projekt with id: " + projektId + " not found");
        }

        List<Zadanie> zadanieList = zadanieRepository.findByProjektId(projektId);
        return new ResponseEntity<>(zadanieList, HttpStatus.OK);
    }

    @GetMapping("/zadanie/{id}")
    public ResponseEntity<Zadanie> getZadanieByProjektId(@PathVariable("id") Long id) {
        Zadanie zadanie = zadanieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Zadanie with id: " + id + " not found"));
        return new ResponseEntity<>(zadanie, HttpStatus.OK);
    }

    @PostMapping("/projekt/{projektId}/zadanie")
    public ResponseEntity<Zadanie> createZadanie(@PathVariable("projektId") Long projektId, @RequestBody Zadanie zadanieRequest) {
        Zadanie zadanie = projektRepository.findById(projektId).map(projekt -> {
            zadanieRequest.setProjekt(projekt);
            return zadanieRepository.save(zadanieRequest);
        }).orElseThrow(() -> new EntityNotFoundException("Projekt with id: " + projektId + " not found"));

        return new ResponseEntity<>(zadanie, HttpStatus.CREATED);
    }

    @DeleteMapping("/zadanie/{id}")
    public ResponseEntity<HttpStatus> deleteZadnie(@PathVariable("id") Long id) {
        zadanieRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
