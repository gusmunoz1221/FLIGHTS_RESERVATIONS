package com.Travel.Travel.domain.entities.jpa;

import com.Travel.Travel.util.AeroLine;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "fly")
public class FlyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double originLat;
    private Double originLng;
    private Double destinyLat;
    private Double destinyLng;
    @Column(length = 20)
    private String originName;
    @Column(length = 20)
    private String destinyName;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private AeroLine aeroLine;

    @ToString.Exclude  //evita la recursividad infinita
    @EqualsAndHashCode.Exclude
    @OneToMany(
            mappedBy = "fly",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, // EAGER: Carga todos los objeto con el ticket
            orphanRemoval = true
    )
    Set<TicketEntity> tickets;
}
