package it.astrignano.tickets.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.astrignano.tickets.model.Ticket;
import it.astrignano.tickets.repository.CategorieRepository;
import it.astrignano.tickets.repository.StatoRepository;
import it.astrignano.tickets.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepo;
	
	@Autowired 
	private CategorieRepository cateRepo;
	
	@Autowired
	private StatoRepository statoRepo;

	@Override
	public Optional<Ticket> findById(Integer id) {
		return ticketRepo.findById(id);
	}

	@Override
	public List<Ticket> findByCategory(Integer id) {
		return cateRepo.getReferenceById(id).getTickets();
	}

	@Override
	public List<Ticket> findAll() {
		return ticketRepo.findAll();
	}

	@Override
	public List<Ticket> findByStato(Integer id) {
		return statoRepo.getReferenceById(id).getTickets();
	}



}
