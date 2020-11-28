package com.rasmoo.cliente.escola.gradecurricular.controller.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.rasmoo.cliente.escola.gradecurricular.dto.MateriaDTO;
import com.rasmoo.cliente.escola.gradecurricular.model.Response;
import com.rasmoo.cliente.escola.gradecurricular.service.MateriaService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(JUnitPlatform.class)
public class MateriaControllerUnitTest {

	@LocalServerPort
	private int port;

	@MockBean
	private MateriaService materiaService;

	@Autowired
	private TestRestTemplate restTemplate;
	
	private static MateriaDTO materiaDto;

	@BeforeAll
	public static void init() {
		
		materiaDto = new MateriaDTO();
		materiaDto.setId(1l);
		materiaDto.setCodigoMateria("lp44b");
		materiaDto.setFrequencia(1);
		materiaDto.setHoras(64);
		materiaDto.setNome("Linguagem de programação");
	}

	@Test
	public void testListarMaterias() {
		Mockito.when(this.materiaService.listar()).thenReturn(new ArrayList<MateriaDTO>());

		ResponseEntity<Response<List<MateriaDTO>>> materias = restTemplate.exchange(
				"http://localhost:" + this.port + "/materia", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<MateriaDTO>>>() {
				});
		
		assertNotNull(materias.getBody().getData());
		assertEquals(200, materias.getBody().getStatusCode());
	}
	
	@Test
	public void testListaPeloId() {
		Mockito.when(this.materiaService.listarPorId(1L)).thenReturn(materiaDto);

		ResponseEntity<Response<MateriaDTO>> materias = restTemplate.exchange(
				"http://localhost:" + this.port + "/materia/1", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<MateriaDTO>>() {
				});
		
		assertNotNull(materias.getBody().getData());
		assertEquals(200, materias.getBody().getStatusCode());
	}
	
	@Test
	public void testCadastrarMaterias() {
		Mockito.when(this.materiaService.cadastra(materiaDto)).thenReturn(Boolean.TRUE);
		
		HttpEntity<MateriaDTO> request = new HttpEntity<MateriaDTO>(materiaDto);

		ResponseEntity<Response<Boolean>> materias = restTemplate.exchange(
				"http://localhost:" + this.port + "/materia", HttpMethod.POST, request,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		
		assertNotNull(materias.getBody().getData());
		assertEquals(201, materias.getBody().getStatusCode());
	}
	
	@Test
	public void testAtualizarMaterias() {
		Mockito.when(this.materiaService.atualizar(materiaDto)).thenReturn(Boolean.TRUE);
		
		HttpEntity<MateriaDTO> request = new HttpEntity<MateriaDTO>(materiaDto);


		ResponseEntity<Response<Boolean>> materias = restTemplate.exchange(
				"http://localhost:" + this.port + "/materia", HttpMethod.PUT, request,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		
		assertNotNull(materias.getBody().getData());
		assertEquals(200, materias.getBody().getStatusCode());
	}
	
	@Test
	public void testExcluirMaterias() {
		Mockito.when(this.materiaService.excluir(1L)).thenReturn(Boolean.TRUE);

		ResponseEntity<Response<Boolean>> materias = restTemplate.exchange(
				"http://localhost:" + this.port + "/materia/1", HttpMethod.DELETE, null,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		
		assertNotNull(materias.getBody().getData());
		assertEquals(200, materias.getBody().getStatusCode());
	}
	
	@Test
	public void testConsultaPelaHora() {
		Mockito.when(this.materiaService.buscaPelaHora(64)).thenReturn(new ArrayList<MateriaDTO>());

		ResponseEntity<Response<List<MateriaDTO>>> materias = restTemplate.exchange(
				"http://localhost:" + this.port + "/materia/hora-minima/64", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<MateriaDTO>>>() {
				});
		
		assertNotNull(materias.getBody().getData());
		assertEquals(200, materias.getBody().getStatusCode());
	}

}
