package it.astrignano.tickets.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.astrignano.tickets.model.Nota;
import it.astrignano.tickets.model.Role;
import it.astrignano.tickets.model.Ticket;
import it.astrignano.tickets.model.User;
import it.astrignano.tickets.repository.CategorieRepository;
import it.astrignano.tickets.repository.NoteRepository;
import it.astrignano.tickets.repository.RoleRepository;
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

	@Autowired
	private NoteRepository noteRepo;

	@Autowired
	private RoleRepository roleRepo;

//-----READ-----
	@GetMapping("/{id}")
	public String show(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal UserDetails currentUser) {

		User user = userRepo.getReferenceById(id);

		Ticket currTicket = ticketRepo.getReferenceById(id);
		User currUser = userRepo.findByUsername(currentUser.getUsername()).get();
		if (!currUser.getRoles().contains(roleRepo.getReferenceById(1))) {
			if (currUser != currTicket.getUtente()) {
				return "/error";
			}
		}
		model.addAttribute("ticket", currTicket);
		model.addAttribute("stati", statoRepo.findAll());

		return "/ticket/show";
	}

//----CREATE----
	@GetMapping("/create")
	public String newTicket(Model model) {

		Ticket newTicket = new Ticket();
		newTicket.setStato(statoRepo.getReferenceById(1));

		List<User> userAttivi = new ArrayList<User>();
		for (User user : userRepo.findAll()) {
			if (user.getRoles().contains(roleRepo.getReferenceById(2))) {
				if (user.getIsAttivo()) {
					userAttivi.add(user);
				}
			}
		}

		model.addAttribute("ticket", newTicket);
		model.addAttribute("operatori", userAttivi);
		model.addAttribute("categorie", cateRepo.findAll());

		return "/ticket/edit";
	}

	@PostMapping("/create")
	public String storeNew(@Valid @ModelAttribute("ticket") Ticket newTicket, BindingResult bindingResult,
			Model model) {

		/*
		 * !!!!!!! AGGIUNGERE VALIDATION UTENTE ATTIVO !!!!!!! IN CASO DI MANOMISSIONE
		 * DA FRONTEND !!!!!!! DELL'ID DELL'UTENTE LIBERO ------- che gli stronzi in
		 * giro sono tanti
		 */

		model.addAttribute("operatori", userRepo.findAll());
		model.addAttribute("categorie", cateRepo.findAll());

		if (bindingResult.hasErrors()) {
			return "/ticket/edit";
		}
		newTicket.setStato(statoRepo.getReferenceById(1));
		ticketRepo.save(newTicket);

		// ---------
		return "redirect:/tickets/" + newTicket.getId();

	}

//-----UPDATE----

	@GetMapping("/edit/{id}")
	public String editTicket(@PathVariable("id") Integer id, Model model) {

		model.addAttribute("ticket", ticketRepo.getReferenceById(id));
		model.addAttribute("operatori", userRepo.findAll());
		model.addAttribute("categorie", cateRepo.findAll());
		model.addAttribute("stati", statoRepo.findAll());
		model.addAttribute("editMode", true);

		return "/ticket/edit";
	}

	@PostMapping("/update/{id}")
	public String updateTicket(@Valid @ModelAttribute("ticket") Ticket upTicket, @PathVariable("id") Integer id,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "/ticket/edit";
		}
		ticketRepo.save(upTicket);

		// ---------
		return "redirect:/tickets/" + upTicket.getId();
	}

//----DELETE-----

	@PostMapping("/delete/{id}")
	public String deleteTicket(@PathVariable("id") Integer idTicket) {

		for (Nota nota : noteRepo.findAll()) {
			if (nota.getTicket().getId() == idTicket) {
				noteRepo.deleteById(nota.getId());
			}
		}

		ticketRepo.deleteById(idTicket);

		return "redirect:/";
	}

//------UPDATE STATO del ticket

	@PostMapping("/stato/{id}")
	public String updateStato(@Valid @ModelAttribute("ticket") Ticket statoTicket, @PathVariable("id") Integer id,
			BindingResult bindingResult) {

		
		
		if (bindingResult.hasErrors()) {
			return "/ticket/show";
		}
		ticketRepo.save(statoTicket);

		// ---------
		return "redirect:/tickets/" + id;

	}

//-------AGGIUNGI NOTA

	@GetMapping("/aggiungi-nota/{id}")
	public String addNota(@PathVariable("id") Integer ticketId, Model model,
			@AuthenticationPrincipal UserDetails currentUser) {

		Ticket ticketRef = ticketRepo.getReferenceById(ticketId);
		Nota newNota = new Nota();
		newNota.setTicket(ticketRef);
		newNota.setUtente(userRepo.findByUsername(currentUser.getUsername()).get());

		model.addAttribute("ticketRef", ticketRef);
		model.addAttribute("nota", newNota);

		return "/note/edit";
	}

//----- get debug
	@GetMapping("")
	public String debug() {
		return "/debug";
	}

}
