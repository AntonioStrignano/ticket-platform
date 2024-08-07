package it.astrignano.tickets.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.astrignano.tickets.model.Role;
import it.astrignano.tickets.model.Ticket;
import it.astrignano.tickets.model.User;
import it.astrignano.tickets.repository.RoleRepository;
import it.astrignano.tickets.repository.TicketRepository;
import it.astrignano.tickets.repository.UserRepository;

@Controller
@RequestMapping("")
public class MainController {

	@Autowired
	private TicketRepository ticketRepo;

	@Autowired
	private UserRepository userRepo;
	
	@Autowired RoleRepository roleRepo;

	@GetMapping("")
	public String landing(Model model, @AuthenticationPrincipal UserDetails currentUser) {

		User currUser = new User();
		for (User user : userRepo.findAll()) {
			if (currentUser.getUsername().equals(user.getUsername())) {
				currUser = userRepo.getReferenceById(user.getId());
				break;
			}
		}

		model.addAttribute("currUser", currUser);
		if(!currUser.getRoles().contains(roleRepo.getReferenceById(2))) {
			return"redirect:/admin";
		} else {
			return "redirect:/user";
		}

	}

	@GetMapping("/admin")
	public String adminDashboard(Model model) {

		model.addAttribute("tickets", ticketRepo.findAll());
		model.addAttribute("utenti", userRepo.findAll());
		Ticket byTitolo = new Ticket();
		model.addAttribute("byTitolo", byTitolo );
		
		

		return "/admin/dashboard";
	}

	@PostMapping("/admin/{titolo}")
	public String ticketByTitolo(Model model, @PathVariable("titolo") String titolo) {

		List<Ticket> ticketFiltered = new ArrayList<Ticket>();
		for (Ticket ticket : ticketRepo.findAll()) {
			if (ticket.getTitolo().equals(titolo)) {
				ticketFiltered.add(ticket);
			}
		}

		model.addAttribute("ticketFiltered", ticketFiltered);

		return "/admin/dashboard-ricerca";
	}
}
