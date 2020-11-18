package com.rasmoo.cliente.escola.gradecurricular.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rasmoo.cliente.escola.gradecurricular.constant.HyperLinkConstantEnum;
import com.rasmoo.cliente.escola.gradecurricular.dto.MateriaDTO;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.model.Response;
import com.rasmoo.cliente.escola.gradecurricular.service.MateriaService;

@RestController
@RequestMapping(value = "/materia")
public class MateriaController {
	
		
	@Autowired
	private MateriaService materiaService;

	@GetMapping
	public ResponseEntity<Response<List<MateriaDTO>>> listaMaterias() {
		Response<List<MateriaDTO>> response = new Response<>();
		response.setData(this.materiaService.listar());
		response.setStatusCode(HttpStatus.OK.value());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
				.methodOn(MateriaController.class)
				.listaMaterias())
				.withSelfRel());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Response<MateriaDTO>> listaMaterias(@PathVariable Long id) {
		Response<MateriaDTO> response = new Response<>();
		MateriaDTO materia = this.materiaService.listarPorId(id);
		response.setData(materia);
		response.setStatusCode(HttpStatus.OK.value());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
				.methodOn(MateriaController.class)
				.listaMaterias(id))
				.withSelfRel());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
				.methodOn(MateriaController.class)
				.deletaMaterias(id))
				.withRel(HyperLinkConstantEnum.EXCLUIR.getValor()));
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
				.methodOn(MateriaController.class)
				.atualizaMateria(materia))
				.withRel(HyperLinkConstantEnum.ATUALIZAR.getValor()));
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping
	public ResponseEntity<Response<Boolean>> cadastarMateria(@Valid @RequestBody MateriaDTO materiaDto) {
		Response<Boolean> response = new Response<>();
		response.setData(this.materiaService.cadastra(materiaDto));
		response.setStatusCode(HttpStatus.CREATED.value());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
				.methodOn(MateriaController.class)
				.cadastarMateria(materiaDto))
				.withSelfRel());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
				.methodOn(MateriaController.class)
				.listaMaterias())
				.withRel(HyperLinkConstantEnum.LISTAR.getValor()));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping
	public ResponseEntity<Response<Boolean>> atualizaMateria(@Valid @RequestBody MateriaDTO materiaDto) {
		Response<Boolean> response = new Response<>();
		response.setData(this.materiaService.atualizar(materiaDto));
		response.setStatusCode(HttpStatus.OK.value());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
				.methodOn(MateriaController.class)
				.atualizaMateria(materiaDto))
				.withSelfRel());
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Response<Boolean>> deletaMaterias(@PathVariable Long id) {
		Response<Boolean> response = new Response<>();
		response.setData(this.materiaService.excluir(id));
		response.setStatusCode(HttpStatus.OK.value());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
				.methodOn(MateriaController.class)
				.deletaMaterias(id))
				.withSelfRel());
		return ResponseEntity.status(HttpStatus.OK).body(response);

		
	}
	
	@GetMapping("/hora-minima/{hora}")
	public ResponseEntity<Response<List<MateriaDTO>>> consultaPelaHora(@PathVariable Integer hora){
		Response<List<MateriaDTO>> response = new Response<>();
		List<MateriaDTO> materia = this.materiaService.buscaPelaHora(hora);
		response.setData(materia);
		response.setStatusCode(HttpStatus.OK.value());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
				.methodOn(MateriaController.class)
				.consultaPelaHora(hora))
				.withSelfRel());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}


}
