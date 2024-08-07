package it.astrignano.tickets.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import it.astrignano.tickets.model.Role;
import it.astrignano.tickets.model.Ticket;
import it.astrignano.tickets.model.User;
import it.astrignano.tickets.repository.RoleRepository;
import it.astrignano.tickets.repository.UserRepository;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

//---- READ
	

	@GetMapping("")
	public String userDashboard (Model model,
			@AuthenticationPrincipal UserDetails currentUser) {


		User currUser = new User();
		for (User utente : userRepo.findAll()) {
			if (utente.getUsername().equals(currentUser.getUsername())) {
				currUser = utente;
				break;
			}
		}

		if(!currUser.getRoles().contains(roleRepo.getReferenceById(2))) {
			return "redirect:/admin";
		} else
		
		model.addAttribute("user", currUser);

		// validazione modifica stato
		Boolean noMoreTickets = true;

		List<Integer> stati = new ArrayList<>();
		for (Ticket ticket : currUser.getTickets()) {
			stati.add(ticket.getStato().getId());
		}
		if (stati.contains(1) || stati.contains(2)) {
			noMoreTickets = false;
		}

		model.addAttribute("noMoreTickets", noMoreTickets);

		return "/user/index";
	}
	
	
	@GetMapping("/{id}")
	public String userAdminSide(@PathVariable("id") Integer id, Model model,
			@AuthenticationPrincipal UserDetails currentUser) {

		User user = userRepo.getReferenceById(id);

		User provUser = new User();
		for (User utente : userRepo.findAll()) {
			if (utente.getUsername().equals(currentUser.getUsername())) {
				provUser = utente;
				break;
			}
		}

		if (!provUser.getRoles().contains(roleRepo.getReferenceById(1))) {
			if (!currentUser.getUsername().equals(user.getUsername())) {
				return "/error";
			}
			else return"user/index";
		}

		model.addAttribute("user", user);

		// validazione modifica stato
		Boolean noMoreTickets = true;

		List<Integer> stati = new ArrayList<>();
		for (Ticket ticket : user.getTickets()) {
			stati.add(ticket.getStato().getId());
		}
		if (stati.contains(1) || stati.contains(2)) {
			noMoreTickets = false;
		}

		model.addAttribute("noMoreTickets", noMoreTickets);

		return "/user/index";
	}

//-----	CREATE
	@GetMapping("/create")
	public String userCreate(Model model) {

		model.addAttribute("user", new User());
		model.addAttribute("ruoli", roleRepo.findAll());

		return "/user/edit";
	}

	@PostMapping("/create")
	public String userStore(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {

		model.addAttribute("ruoli", roleRepo.findAll());

		if (bindingResult.hasErrors()) {
			return "/user/edit";
		}
		user.setIsAttivo(true);
		user.setPassword("{noop}" + user.getPassword());

		userRepo.save(user);

//-------------- lista degli utenti/pag utenti dell'admin
		return "redirect:/user/" + user.getId();
	}

// ------ UPDATE ATTIVITA
	@PostMapping("/flag/{id}")
	public String flagAttivita(@ModelAttribute("user") User user, @PathVariable("id") Integer id) {

		userRepo.save(user);

		return "redirect:/user/" + user.getId();

	}

//-----	UPDATE

	@GetMapping("/update/{id}")
	public String userEdit(Model model, @PathVariable("id") Integer id) {

		Boolean editMode = true;
		User user = userRepo.getReferenceById(id);
//	user.setPassword(user.getPassword().substring(6));
		user.setPassword(null);
		model.addAttribute("user", user);
		model.addAttribute("ruoli", roleRepo.findAll());
		model.addAttribute("editMode", editMode);

		return "/user/edit";
	}

	@PostMapping("/update/{id}")
	public String userUpdate(@PathVariable("id") Integer id, @Valid @ModelAttribute("user") User user,
			BindingResult bindingResult) {

		user.setPassword("{noop}" + user.getPassword());

		if (bindingResult.hasErrors()) {
			return "/user/edit";
		}

		userRepo.save(user);

		return "redirect:/user/{id}";
	}

}
