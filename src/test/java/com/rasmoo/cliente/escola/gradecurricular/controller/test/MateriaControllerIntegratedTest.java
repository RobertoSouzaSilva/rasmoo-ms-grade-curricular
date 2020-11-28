package com.rasmoo.cliente.escola.gradecurricular.controller.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.rasmoo.cliente.escola.gradecurricular.dto.MateriaDTO;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.model.Response;
import com.rasmoo.cliente.escola.gradecurricular.repository.MateriaRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(JUnitPlatform.class)
public class MateriaControllerIntegratedTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private MateriaRepository materiaRepository;
	

	@BeforeEach
	public void init() {
		this.montaBaseDeDados();
	}
	
	@AfterEach
	public void finish() {
		this.materiaRepository.deleteAll();
	}
	
	private void montaBaseDeDados() {
		MateriaEntity m1 = new MateriaEntity();
		m1.setCodigoMateria("pf38c");
		m1.setFrequencia(2);
		m1.setHoras(34);
		m1.setNome("programação funcional");
		
		MateriaEntity m2 = new MateriaEntity();
		m1.setCodigoMateria("lp28a");
		m1.setFrequencia(2);
		m1.setHoras(86);
		m1.setNome("linguagem de programação");
		
		MateriaEntity m3 = new MateriaEntity();
		m1.setCodigoMateria("poo55e");
		m1.setFrequencia(1);
		m1.setHoras(102);
		m1.setNome("programação orientada a objetos");
		
		this.materiaRepository.saveAll(Arrays.asList(m1, m2, m3));
	}

	@Test
	public void testListarMaterias() {

		ResponseEntity<Response<List<MateriaDTO>>> materias = restTemplate.exchange(
				"http://localhost:" + this.port + "/materia", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<MateriaDTO>>>() {
				});
		
		assertNotNull(materias.getBody().getData());
		assertEquals(3, materias.getBody().getData().size());
		
		assertEquals(200, materias.getBody().getStatusCode());
	}
	
	@Test
	public void testListaPeloId() {
		
		List<MateriaEntity> materiasList = this.materiaRepository.findAll();
		Long id = materiasList.get(0).getId();
		
		ResponseEntity<Response<MateriaDTO>> materias = restTemplate.exchange(
				"http://localhost:" + this.port + "/materia/"+id, HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<MateriaDTO>>() {
				});
		
		assertNotNull(materias.getBody().getData());
		assertEquals("programação orientada a objetos", materias.getBody().getData().getNome());
		assertEquals(id, materias.getBody().getData().getId());
		assertEquals(200, materias.getBody().getStatusCode());
	}
	
	
	@Test
	public void testAtualizaMateria() {
		
		List<MateriaEntity> materiasList = this.materiaRepository.findAll();
		MateriaEntity materia = materiasList.get(0);
		
		materia.setNome("Teste Atualiza Materia");
		
		HttpEntity<MateriaEntity> request = new HttpEntity<>(materia);
		
		
		ResponseEntity<Response<Boolean>> materias = restTemplate.exchange(
				"http://localhost:" + this.port + "/materia/", HttpMethod.PUT, request,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		
		MateriaEntity materiaAtualizada = this.materiaRepository.findById(materia.getId()).get();
		
		assertTrue(materias.getBody().getData());
		assertEquals("Teste Atualiza Materia", materiaAtualizada.getNome());
		assertEquals(200, materias.getBody().getStatusCode());
	}
	
	@Test
	public void testCadastraMateria() {
		
		MateriaEntity m4 = new MateriaEntity();
		m4.setCodigoMateria("grafos11g");
		m4.setFrequencia(1);
		m4.setHoras(55);
		m4.setNome("Grafos e automatos");
		
		
		HttpEntity<MateriaEntity> request = new HttpEntity<>(m4);
		
		
		ResponseEntity<Response<Boolean>> materias = restTemplate.exchange(
				"http://localhost:" + this.port + "/materia/", HttpMethod.POST, request,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		
		List<MateriaEntity> listMateriaAtualizada = this.materiaRepository.findAll();
		
		assertTrue(materias.getBody().getData());
		assertEquals(4, listMateriaAtualizada.size());
		assertEquals(201, materias.getBody().getStatusCode());
	}
	
	
	
	@Test
	public void testConsultaPelaHora() {

		ResponseEntity<Response<List<MateriaDTO>>> materias = restTemplate.exchange(
				"http://localhost:" + this.port + "/materia/hora-minima/64", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<MateriaDTO>>>() {
				});
		
		assertNotNull(materias.getBody().getData());
		assertEquals(1, materias.getBody().getData().size());
		assertEquals(200, materias.getBody().getStatusCode());
	}

	@Test
	public void testExluirPorId() {
		
		List<MateriaEntity> materiasList = this.materiaRepository.findAll();
		Long id = materiasList.get(0).getId();
		
		ResponseEntity<Response<Boolean>> materias = restTemplate.exchange(
				"http://localhost:" + this.port + "/materia/"+id, HttpMethod.DELETE, null,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		
		List<MateriaEntity> listMateriaAtualizada = this.materiaRepository.findAll();
		
		assertTrue(materias.getBody().getData());
		assertEquals(2, listMateriaAtualizada.size());
		assertEquals(200, materias.getBody().getStatusCode());
	}
}
