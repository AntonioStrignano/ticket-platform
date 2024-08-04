package it.astrignano.tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.astrignano.tickets.model.Stato;

public interface StatoRepository extends JpaRepository<Stato, Integer>{

}
