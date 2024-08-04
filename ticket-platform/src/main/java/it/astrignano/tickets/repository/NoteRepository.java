package it.astrignano.tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.astrignano.tickets.model.Nota;

public interface NoteRepository extends JpaRepository<Nota, Integer> {

}
