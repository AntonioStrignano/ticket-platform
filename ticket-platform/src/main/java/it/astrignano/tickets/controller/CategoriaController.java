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

import it.astrignano.tickets.model.Categoria;
import it.astrignano.tickets.repository.CategorieRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/categorie")
public class CategoriaController {
	
	@Autowired
	private CategorieRepository catRepo;
	
//----- READ
	
@GetMapping("/")
public String catList (Model model) {
	
	model.addAttribute("categorie", catRepo.findAll());
	
	return "/categoria/lista";
}
	
	
	
//-----	CREATE
	
@GetMapping("/new")
public String newCat(Model model) {
	
	model.addAttribute("categoria", new Categoria());
	
	return "/categoria/edit";
}
	
@PostMapping("/create")
public String storeNewCat(Model model, @Valid @ModelAttribute("categoria") Categoria newCat, BindingResult bindingResult) {
	
	if(bindingResult.hasErrors()) {
		return "/categoria/edit";
	}
	
	catRepo.save(newCat);
	
	return "redirect:/categorie";
}
	
	
	
//-----	UPDATE
	
@GetMapping("/{id}/edit")
public String editCat(Model model, @PathVariable ("id") Integer id) {
	
	model.addAttribute("categoria", catRepo.getReferenceById(id));
	Boolean isEdit = true;
	model.addAttribute("idEdit", isEdit);
	
	return"/categoria/edit";
}


@PostMapping("/{id}/update")
public String updateCat(@Valid @ModelAttribute ("categoria") Categoria catUpdate, BindingResult bindingResult) {
	
	if(bindingResult.hasErrors()) {
		return "/categoria/edit";
	}
	
	catRepo.save(catUpdate);
	
	return "redirect:/categorie";
}
	
	
//-----	DELETE

@PostMapping("{id}/delete")
public String deleteCat(@PathVariable ("id") Integer id) {
	
	catRepo.deleteById(id);
	
	return "redirect:/categorie";
}

}
