package com.vedruna.transporte.CoDrive.persistance.models;

public enum Rol {
    ADMIN,
    USUARIO,
    CONDUCTOR;

    // Método para verificar si el rol es ADMIN
    public boolean esAdmin() {
        return this == ADMIN;
    }

    // Método para verificar si el rol es CONDUCTOR
    public boolean esConductor() {
        return this == CONDUCTOR;
    }

    // Método para verificar si el rol es USUARIO
    public boolean esUsuario() {
        return this == USUARIO;
    }
}
