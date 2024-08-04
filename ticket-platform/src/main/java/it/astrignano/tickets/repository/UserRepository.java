package it.astrignano.tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.astrignano.tickets.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
