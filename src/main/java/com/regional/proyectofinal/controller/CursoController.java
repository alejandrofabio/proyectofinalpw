package com.regional.proyectofinal.controller;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.regional.proyectofinal.model.Curso;
import com.regional.proyectofinal.repository.AlumnoRepository;
import com.regional.proyectofinal.repository.CursoRepository;
import com.regional.proyectofinal.repository.ProfesorRepository;

@Controller
@RequestMapping("/curso")
public class CursoController {

	@Autowired
	private CursoRepository repo;

	@Autowired
	private ProfesorRepository profesorRepo;

	@Autowired
	private AlumnoRepository alumnoRepo;

	@GetMapping("/listar")
	public String listar(Model model) {
		model.addAttribute("cursos", repo.findAll());
		return "curso/lista";
	}

	@GetMapping("/nuevo")
	public String nuevo(Model model) {
		model.addAttribute("cursoForm", new Curso());
		model.addAttribute("profesores", profesorRepo.findAll());
		model.addAttribute("alumnos", alumnoRepo.findAll());
		return "curso/formulario";
	}

	@PostMapping("/guardar")
	public String guardar(@ModelAttribute Curso curso,
			@RequestParam(required = false) Long profesorId,
			@RequestParam(required = false) List<Long> alumnosIds) {
		if (profesorId != null) {
			curso.setProfesor(profesorRepo.findById(profesorId).get());
		} else {
			curso.setProfesor(null);
		}
		if (alumnosIds != null) {
			curso.setAlumnos(new HashSet<>(alumnoRepo.findAllById(alumnosIds)));
		}
		repo.save(curso);
		return "redirect:/curso/listar";
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Long id, Model model) {
		Curso curso = repo.findById(id).get();
		model.addAttribute("cursoForm", curso);
		model.addAttribute("profesores", profesorRepo.findAll());
		model.addAttribute("alumnos", alumnoRepo.findAll());
		return "curso/formulario";
	}

	@GetMapping("/ver/{id}")
	public String ver(@PathVariable Long id, Model model) {
		Curso curso = repo.findById(id).get();
		model.addAttribute("curso", curso);
		return "curso/detalle";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id) {
		repo.deleteById(id);
		return "redirect:/curso/listar";
	}
}
