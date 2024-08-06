package it.astrignano.tickets.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.astrignano.tickets.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByUsername(String username);

}
