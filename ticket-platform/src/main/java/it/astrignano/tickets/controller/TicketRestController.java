package it.astrignano.tickets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.astrignano.tickets.model.Ticket;
import it.astrignano.tickets.repository.TicketRepository;
import it.astrignano.tickets.response.Payload;
import it.astrignano.tickets.service.TicketService;

@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
public class TicketRestController {
	@Autowired
	private TicketService ticketService;

// elenco tickets	
	@GetMapping("")
	public ResponseEntity<Payload<List<Ticket>>> get() {

		List<Ticket> tickets = ticketService.findAll();

		if (!tickets.isEmpty()) {
			return ResponseEntity.ok(new Payload<List<Ticket>>(tickets, null, HttpStatus.OK));
		} else {
			return new ResponseEntity<Payload<List<Ticket>>>(new Payload<List<Ticket>>(null,
					"La lista di ticket è vuota o non è stata trovata", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
		}

	}

//Tickets per categoria
	@GetMapping("/category/{categoria}")
	public ResponseEntity<Payload<List<Ticket>>> getByCategory(@PathVariable("categoria") Integer idCat) {

		List<Ticket> tickets = ticketService.findByCategory(idCat);
		if (!tickets.isEmpty()) {
			return ResponseEntity.ok(new Payload<List<Ticket>>(tickets, null, HttpStatus.OK));
		} else {
			return new ResponseEntity<Payload<List<Ticket>>>(new Payload<List<Ticket>>(null,
					"La lista di ticket è vuota o non è stata trovata", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
		}
	}

//tickets per stato
	@GetMapping("/status/{stato}")
	public ResponseEntity<Payload<List<Ticket>>> getByStatus(@PathVariable("stato") Integer idStat) {

		List<Ticket> tickets = ticketService.findByStato(idStat);
		if (!tickets.isEmpty()) {
			return ResponseEntity.ok(new Payload<List<Ticket>>(tickets, null, HttpStatus.OK));
		} else {
			return new ResponseEntity<Payload<List<Ticket>>>(new Payload<List<Ticket>>(null,
					"La lista di ticket è vuota o non è stata trovata", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
		}

	}

}
