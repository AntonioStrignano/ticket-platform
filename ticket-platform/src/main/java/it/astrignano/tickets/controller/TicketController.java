package it.astrignano.tickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.astrignano.tickets.model.Nota;
import it.astrignano.tickets.model.Ticket;
import it.astrignano.tickets.repository.CategorieRepository;
import it.astrignano.tickets.repository.StatoRepository;
import it.astrignano.tickets.repository.TicketRepository;
import it.astrignano.tickets.repository.UserRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	private TicketRepository ticketRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CategorieRepository cateRepo;
	
	@Autowired
	private StatoRepository statoRepo;

//-----READ-----
	@GetMapping("/{id}")
	public String show(@PathVariable("id") Integer id, Model model) {

		model.addAttribute("ticket", ticketRepo.findById(id).get());
		model.addAttribute("stati", statoRepo.findAll());

		return "/ticket/show";
	}

//----CREATE----
	@GetMapping("/new")
	public String newTicket(Model model) {

		model.addAttribute("ticket", new Ticket());
		model.addAttribute("operatori", userRepo.findAll());
		model.addAttribute("categorie", cateRepo.findAll());

		return "/ticket/edit";
	}

	@PostMapping("/create")
	public String storeNew(@Valid @ModelAttribute("ticket") Ticket newTicket, BindingResult bindingResult) {

		/*
		 * !!!!!!! AGGIUNGERE VALIDATION UTENTE ATTIVO !!!!!!! IN CASO DI MANOMISSIONE
		 * DA FRONTEND !!!!!!! DELL'ID DELL'UTENTE LIBERO ------- che gli stronzi in
		 * giro sono tanti
		 */

		if (bindingResult.hasErrors()) {
			return "/ticket/edit";
		}
		ticketRepo.save(newTicket);

		//---------
		return "redirect:/";
	}

//-----UPDATE----

	@GetMapping("/{id}/edit")
	public String editTicket(@PathVariable("id") Integer id, Model model) {

		model.addAttribute("ticket", ticketRepo.getReferenceById(id));
		model.addAttribute("operatori", userRepo.findAll());
		model.addAttribute("categorie", cateRepo.findAll());

		return "/ticket/edit";
	}

	@PostMapping("/{id}/update")
	public String updateTicket(@Valid @ModelAttribute("ticket") Ticket upTicket, @PathVariable("id") Integer id,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "/ticket/edit";
		}
		ticketRepo.save(upTicket);

		//---------
		return "redirect:/";
	}

//----DELETE-----

	@PostMapping("/{id}/delete")
	public String deleteTicket(@PathVariable ("id") Integer id) {
		
		ticketRepo.deleteById(id);
		
		return"redirect:/";
	}
	
	
	
//------UPDATE STATO del ticket
	
	@PostMapping("/{id}/stato")
	public String updateStato(@Valid @ModelAttribute("ticket") Ticket statoTicket, @PathVariable("id") Integer id,
			BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "/ticket/show";
		}
		ticketRepo.save(statoTicket);

		//---------
		return "redirect:/";
	
	}
	
	
//-------AGGIUNGI NOTA
	
	@GetMapping("/{id}/aggiungi-nota")
	public String addNota(@PathVariable ("id") Integer ticketId, Model model) {
		
		model.addAttribute("nota", new Nota());
		model.addAttribute("ticketRef", ticketRepo.getReferenceById(ticketId));
		
		return "/nota/edit";
	}
	
//-----	ADMIN DASHBOARD
	
}
