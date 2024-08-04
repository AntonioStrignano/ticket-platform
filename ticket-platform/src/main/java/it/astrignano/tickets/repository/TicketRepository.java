package it.astrignano.tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.astrignano.tickets.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{

}
