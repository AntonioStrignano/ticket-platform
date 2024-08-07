package it.astrignano.tickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.astrignano.tickets.model.Ticket;
import it.astrignano.tickets.repository.TicketRepository;
import it.astrignano.tickets.service.TicketService;

@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
public class TicketRestController {
	@Autowired
	private  TicketService ticketService;

// elenco tickets	
@GetMapping("")
public ResponseEntity<Ticket> get(){
	
	
}


//Tickets per categoria
@GetMapping("/{categoria}")

//tickets per stato
@GetMapping("/{stato}")
	
}
