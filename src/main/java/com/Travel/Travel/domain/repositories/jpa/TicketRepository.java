package com.Travel.Travel.domain.repositories.jpa;

import com.Travel.Travel.domain.entities.jpa.TicketEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface TicketRepository  extends CrudRepository<TicketEntity, UUID> {
}
