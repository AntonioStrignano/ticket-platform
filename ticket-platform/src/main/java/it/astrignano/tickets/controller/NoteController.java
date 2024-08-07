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

	@PostMapping("/create")
	public String createNota(@Valid @ModelAttribute("nota") Nota newNota, @ModelAttribute("ticketRef") Ticket ticketRef,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "/note/edit";
		}

		noteRepo.save(newNota);

		return "redirect:/tickets/" + newNota.getTicket().getId();
	}

//----- UPDATE
	@GetMapping("/edit/{id}")
	public String editNota(@PathVariable("id") Integer id, Model model) {

		model.addAttribute("nota", noteRepo.getReferenceById(id));
		model.addAttribute("editMode", true);

		return "/note/edit";
	}

	@PostMapping("/edit/{id}")
	public String notaEditedStore(@Valid @ModelAttribute("nota") Nota nota, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "/nota/edit";
		}

		noteRepo.save(nota);

		return "redirect:/tickets/" + nota.getTicket().getId();
	}

//----- DELETE

	@PostMapping("/delete/{id}")
	public String notaDel(@PathVariable("id") Integer id) {

		Ticket ticketRef = noteRepo.getReferenceById(id).getTicket();
		noteRepo.deleteById(id);

		return "redirect:/tickets/" + ticketRef.getId();
	}

}
