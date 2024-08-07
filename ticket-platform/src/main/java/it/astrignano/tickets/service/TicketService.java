package it.astrignano.tickets.service;

import java.util.List;
import java.util.Optional;

import it.astrignano.tickets.model.Ticket;

public interface TicketService {

	public List<Ticket> findAll();

	public List<Ticket> findByCategory(Integer id);
	
	public List<Ticket> findByStato(Integer id);
	
	
}
