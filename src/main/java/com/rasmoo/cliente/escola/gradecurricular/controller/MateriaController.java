package com.rasmoo.cliente.escola.gradecurricular.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.repository.MateriaRepository;

@RestController
@RequestMapping(value = "/materia")
public class MateriaController {
	
	@Autowired
	private MateriaRepository materiaRepository;

	@GetMapping
	public ResponseEntity<List<MateriaEntity>> listaMaterias() {
		return ResponseEntity.status(HttpStatus.OK).body(this.materiaRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MateriaEntity> listaMaterias(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(this.materiaRepository.findById(id).get());
	}

	@PostMapping
	public ResponseEntity<Boolean> cadastarMateria(@RequestBody MateriaEntity materia) {
		try {
			this.materiaRepository.save(materia);
			return ResponseEntity.status(HttpStatus.OK).body(true);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body(false);
		}
	}

}
