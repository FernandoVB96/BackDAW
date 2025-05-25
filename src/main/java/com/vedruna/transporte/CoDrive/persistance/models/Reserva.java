package com.vedruna.transporte.CoDrive.persistance.models;

import java.util.Date;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "viaje_id")
    private Viaje viaje;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaReserva;

    private String estado;

    public void confirmar() {
        this.estado = "CONFIRMADA";
    }

    public void cancelar() {
        this.estado = "CANCELADA";
    }

    public boolean esModificable() {
        return !("CANCELADA".equalsIgnoreCase(estado) || "CONFIRMADA".equalsIgnoreCase(estado));
    }
}
