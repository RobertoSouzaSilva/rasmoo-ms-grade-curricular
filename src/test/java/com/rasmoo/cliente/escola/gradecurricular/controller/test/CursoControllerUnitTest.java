package com.rasmoo.cliente.escola.gradecurricular.controller.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.rasmoo.cliente.escola.gradecurricular.entity.CursoEntity;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.model.CursoModel;
import com.rasmoo.cliente.escola.gradecurricular.model.Response;
import com.rasmoo.cliente.escola.gradecurricular.service.CursoService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(JUnitPlatform.class)
public class CursoControllerUnitTest {
	
	@LocalServerPort
	private int port;

	@MockBean
	private CursoService cursoService;

	@Autowired
	private TestRestTemplate restTemplate;
	
	public static CursoModel cursoModel;
	
	
	@BeforeAll
	public static void init() {
		cursoModel = new CursoModel();
		cursoModel.setCodigoCurso("cc10");
		cursoModel.setNome("Ciencia da Computação");
	}
	
	@Test
	public void testListaCurso() {
		Mockito.when(this.cursoService.listar()).thenReturn(new ArrayList<CursoEntity>());
		
		ResponseEntity<Response<List<CursoEntity>>> cursos = restTemplate.exchange(
				"http://localhost:" + this.port + "/curso", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<CursoEntity>>>() {
				});
		
		assertNotNull(cursos.getBody().getData());
		assertEquals(200, cursos.getBody().getStatusCode());
		
	}
	
	@Test
	public void testConsultarCurso() {
		
		
		
		CursoModel curso = new CursoModel();
		curso.setId(1L);
		curso.setCodigoCurso("ENGCOMP");
		curso.setNome("ENGENHARIA DA COMPUTACAO");
		curso.setMaterias(Arrays.asList(1L, 2L));
		Mockito.when(this.cursoService.consultaPorCodigo("ENGCOMP")).thenReturn(curso);

		ResponseEntity<Response<List<CursoModel>>> cursos = restTemplate.exchange(
				"http://localhost:" + this.port + "/curso/ENGCOMP", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<CursoModel>>>() {
				});
		
		assertNotNull(cursos.getBody().getData());
		assertEquals(200, cursos.getBody().getStatusCode());
	}
	
	@Test
	public void testCadastrarCursos() {
		Mockito.when(this.cursoService.cadastrar(cursoModel)).thenReturn(Boolean.TRUE);
		
		HttpEntity<CursoModel> request = new HttpEntity<CursoModel>(cursoModel);

		ResponseEntity<Response<Boolean>> cursos = restTemplate.exchange(
				"http://localhost:" + this.port + "/materia", HttpMethod.POST, request,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		
		assertNotNull(cursos.getBody().getData());
		assertEquals(201, cursos.getBody().getStatusCode());
	}
	
	


}
