package it.astrignano.tickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import it.astrignano.tickets.model.User;
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

//---- READ
	@GetMapping("/{id}")
	public String userDashboard(@PathVariable("id") Integer id, Model model) {

		model.addAttribute("user", userRepo.getReferenceById(id));

		return "/user/dashboard";
	}

//-----	CREATE
	@GetMapping("/new")
	public String userCreate(Model model) {

		model.addAttribute("user", new User());

		return "/user/edit";
	}

	@PostMapping("/create")
	public String userStore(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "/user/edit";
		}

		userRepo.save(user);

//-------------- lista degli utenti/pag utenti dell'admin
		return "redirect:/";
	}

//-----	UPDATE

	@PostMapping("/{id}/update")
	public String userUpdate(@PathVariable("id") Integer id, @Valid @ModelAttribute("user") User user,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "/user/dashboard";
		}

		userRepo.save(user);

		return "redirect:/user/{id}";
	}

//-----	DELETE
}
