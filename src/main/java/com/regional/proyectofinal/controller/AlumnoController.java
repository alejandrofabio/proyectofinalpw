package com.regional.proyectofinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.regional.proyectofinal.model.Alumno;
import com.regional.proyectofinal.model.Curso;
import com.regional.proyectofinal.repository.AlumnoRepository;
import com.regional.proyectofinal.repository.CursoRepository;

@Controller
@RequestMapping("/alumno")
public class AlumnoController {

	@Autowired
	private AlumnoRepository repo;

	@Autowired
	private CursoRepository cursoRepo;

	@GetMapping("/listar")
	public String listar(Model model) {
		model.addAttribute("alumnos", repo.findAll());
		return "alumno/lista";
	}

	@GetMapping("/nuevo")
	public String nuevo(Model model) {
		model.addAttribute("alumnoForm", new Alumno());
		return "alumno/formulario";
	}

	@PostMapping("/guardar")
	public String guardar(@ModelAttribute Alumno alumno) {
		repo.save(alumno);
		return "redirect:/alumno/listar";
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Long id, Model model) {
		Alumno alumno = repo.findById(id).get();
		model.addAttribute("alumnoForm", alumno);
		return "alumno/formulario";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id) {
		Alumno alumno = repo.findById(id).get();
		for (Curso curso : alumno.getCursos()) {
			curso.getAlumnos().remove(alumno);
			cursoRepo.save(curso);
		}
		repo.deleteById(id);
		return "redirect:/alumno/listar";
	}
}
