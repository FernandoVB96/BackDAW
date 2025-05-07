package com.vedruna.transporte.CoDrive.persistance.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;


@Entity
public class Conductor extends Usuario {

    @OneToMany(mappedBy = "conductor", cascade = CascadeType.ALL)
    private List<Vehiculo> vehiculos;
    
    @OneToMany(mappedBy = "conductor")
    private List<Viaje> viajes;
}

