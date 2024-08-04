package it.astrignano.tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.astrignano.tickets.model.Categoria;

public interface CategorieRepository extends JpaRepository<Categoria, Integer>{

}
