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
import it.astrignano.tickets.repository.NoteRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/note")
public class NoteController {

	@Autowired
	private NoteRepository noteRepo;

//-----	CREATE

	@PostMapping("/new")
	public String createNota(@Valid @ModelAttribute("nota") Nota newNota, @ModelAttribute("ticketRef") Ticket ticketRef,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "/ticket/add-nota";
		}

		newNota.setTicket(ticketRef);
		noteRepo.save(newNota);

		return "redirect:/";
	}

//----- UPDATE
	@GetMapping("/{id}/edit")
	public String editNota(@PathVariable("id") Integer id, Model model) {

		model.addAttribute("nota", noteRepo.getReferenceById(id));

		return "/nota/edit";
	}

	@PostMapping("/{id}/edit")
	public String notaEditedStore(@Valid @ModelAttribute("nota") Nota nota, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "/nota/edit";
		}

		noteRepo.save(nota);

		return "redirect:/";
	}

//----- DELETE

	@PostMapping("/{id}/delete")
	public String notaDel(@PathVariable ("id") Integer id, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "/";
		}
		
		noteRepo.deleteById(id);
		
		return "redirect:/";
	}

}
