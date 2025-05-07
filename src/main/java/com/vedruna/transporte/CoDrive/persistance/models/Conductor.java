package com.vedruna.transporte.CoDrive.persistance.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Conductor extends Usuario {

    private String licencia;

    @OneToMany(mappedBy = "conductor", cascade = CascadeType.ALL)
    private List<Vehiculo> vehiculos;
    
    @OneToMany(mappedBy = "conductor")
    private List<Viaje> viajes;
}

