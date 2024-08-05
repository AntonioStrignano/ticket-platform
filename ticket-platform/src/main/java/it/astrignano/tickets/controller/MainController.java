package it.astrignano.tickets.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import it.astrignano.tickets.model.Ticket;
import it.astrignano.tickets.repository.TicketRepository;
import it.astrignano.tickets.repository.UserRepository;

@Controller
@RequestMapping("")
public class MainController {
	
	@Autowired
	private TicketRepository ticketRepo;

	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("")
	public String landingPage() {

		return "/index";
	}

	@GetMapping("/admin")
	public String adminDashboard(Model model) {

		model.addAttribute("tickets", ticketRepo.findAll());
		model.addAttribute("utenti", userRepo.findAll());
		
		return "/admin/dashboard";
	}
	
	@GetMapping("/admin/{titolo}")
	public String ticketByTitolo(Model model, @PathVariable("titolo") String titolo) {
		
		List<Ticket> ticketFiltered = new ArrayList<Ticket>();
		for(Ticket ticket:ticketRepo.findAll()) {
			if(ticket.getTitolo().equals(titolo)) {
				ticketFiltered.add(ticket);
			}
		}
		
		model.addAttribute("ticketFiltered", ticketFiltered);
		model.addAttribute("titoloRicerca", titolo);
		
		return"/admin/dashboard-ricerca";
	}
}
