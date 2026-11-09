package com.regional.proyectofinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.regional.proyectofinal.repository.AlumnoRepository;
import com.regional.proyectofinal.repository.CursoRepository;
import com.regional.proyectofinal.repository.ProfesorRepository;

@Controller
public class InicioController {

	@Autowired
	private AlumnoRepository alumnoRepo;

	@Autowired
	private ProfesorRepository profesorRepo;

	@Autowired
	private CursoRepository cursoRepo;

	@GetMapping("/")
	public String inicio(Model model) {
		model.addAttribute("totalAlumnos", alumnoRepo.count());
		model.addAttribute("totalProfesores", profesorRepo.count());
		model.addAttribute("totalCursos", cursoRepo.count());
		return "index";
	}
}
