package com.vedruna.transporte.CoDrive.persistance.models;

public enum Rol {
    ADMIN,
    USUARIO,
    CONDUCTOR;

    public boolean esAdmin() {
        return this == ADMIN;
    }

    public boolean esConductor() {
        return this == CONDUCTOR;
    }

    public boolean esUsuario() {
        return this == USUARIO;
    }
}
