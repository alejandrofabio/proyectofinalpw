package com.regional.proyectofinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.regional.proyectofinal.model.Curso;
import com.regional.proyectofinal.model.Profesor;
import com.regional.proyectofinal.repository.CursoRepository;
import com.regional.proyectofinal.repository.ProfesorRepository;

@Controller
@RequestMapping("/profesor")
public class ProfesorController {

	@Autowired
	private ProfesorRepository repo;

	@Autowired
	private CursoRepository cursoRepo;

	@GetMapping("/listar")
	public String listar(Model model) {
		model.addAttribute("profesores", repo.findAll());
		return "profesor/lista";
	}

	@GetMapping("/nuevo")
	public String nuevo(Model model) {
		model.addAttribute("profesorForm", new Profesor());
		return "profesor/formulario";
	}

	@PostMapping("/guardar")
	public String guardar(@ModelAttribute Profesor profesor) {
		repo.save(profesor);
		return "redirect:/profesor/listar";
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Long id, Model model) {
		Profesor profesor = repo.findById(id).get();
		model.addAttribute("profesorForm", profesor);
		return "profesor/formulario";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id) {
		Profesor profesor = repo.findById(id).get();
		for (Curso curso : profesor.getCursos()) {
			curso.setProfesor(null);
			cursoRepo.save(curso);
		}
		repo.deleteById(id);
		return "redirect:/profesor/listar";
	}
}
