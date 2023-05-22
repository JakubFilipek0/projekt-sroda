package com.example.projektsroda.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"appUsers"})
public class Projekt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projekt_id")
    private Long id;

    private String nazwa;
    private String opis;

    @ManyToMany
    @JoinTable(name = "projekt_users",
            joinColumns = {@JoinColumn(name = "projekt_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<AppUser> appUsers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Set<AppUser> getAppUsers() {
        return appUsers;
    }

    public void setAppUsers(Set<AppUser> appUsers) {
        this.appUsers = appUsers;
    }
}
