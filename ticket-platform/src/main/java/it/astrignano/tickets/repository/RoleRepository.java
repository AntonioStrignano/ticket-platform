package it.astrignano.tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.astrignano.tickets.model.Role;


public interface RoleRepository extends JpaRepository<Role, Integer>{

}
