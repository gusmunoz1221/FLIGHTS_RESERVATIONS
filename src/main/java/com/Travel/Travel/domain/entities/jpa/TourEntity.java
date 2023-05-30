package com.Travel.Travel.domain.entities.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "tour")
public class TourEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            mappedBy = "tour",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, // EAGER: Carga todos los objeto con el ticket
            orphanRemoval = true
    )
    private Set<TicketEntity> tickets;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            mappedBy = "tour",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, // EAGER: Carga todos los objeto con el ticket
            orphanRemoval = true
    )
    private Set<ReservationEntity> reservations;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private CustomerEntity customer;

//------------------??????????---------------------------
    @PrePersist
    @PreRemove
    public void updateFk(){
        tickets.forEach(ticket -> ticket.setTour(this));
        reservations.forEach(reservations -> reservations.setTour(this));

    }

    public void addTicket(TicketEntity ticketEntity){
        if(Objects.isNull(ticketEntity))
            tickets = new HashSet<>();
        tickets.add(ticketEntity);
        tickets.forEach(ticket -> ticket.setTour(this));
    }

    public void removeTicket(UUID id){
        tickets.forEach(ticket -> {
            if(ticket.getId().equals(id))
                ticket.setTour(null);
        });
    }

    public void addReservation(ReservationEntity reservationEntity){
        if(Objects.isNull(reservationEntity))
            tickets = new HashSet<>();
        reservations.add(reservationEntity);
        reservations.forEach(reservation -> reservation.setTour(this));
    }


    //quitamos la llave foranea es decir la union entre las dos tablas
    public void removeReservation(UUID id){
        reservations.forEach(reservation -> {
            if(reservation.getId().equals(id))
                reservation.setTour(null);
        });
    }




  /***************PROBLEMA CON LA BASE DE DATOS***************

   // mapeando relaciones inversas para tickets
    public void addTicket(TicketEntity ticket){
        if(Objects.isNull(this.tickets))
            this.tickets = new HashSet<>();
        this.tickets.add(ticket);
    }

    public void removeTicket(UUID id){
        if(Objects.isNull(this.tickets))
            this.tickets = new HashSet<>();
        this.tickets.removeIf(ticket -> ticket.getId().equals(id));
    }

    public void updateTicket(){
        this.tickets.forEach(ticket -> ticket.setTour(this));
    }

    // mapeando relaciones inversas para reservations
    public void addReservation(ReservationEntity reservation){
        if(Objects.isNull(this.reservations))
            this.reservations = new HashSet<>();
        this.reservations.add(reservation);
    }

    public void removeReservation(UUID id){
        if(Objects.isNull(this.tickets))
            this.reservations = new HashSet<>();
        this.reservations.removeIf(reservation -> reservation.getId().equals(id));
    }

    public void updateReservations(){
        this.reservations.forEach(ticket -> ticket.setTour(this));
    }*/
}
