package com.rasmoo.cliente.escola.gradecurricular.controller.test;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import com.rasmoo.cliente.escola.gradecurricular.controller.CursoController;
import com.rasmoo.cliente.escola.gradecurricular.entity.CursoEntity;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.repository.CursoRepository;
import com.rasmoo.cliente.escola.gradecurricular.repository.MateriaRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(JUnitPlatform.class)
public class CursoControllerIntrgratedTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private CursoRepository cursoRespository;
	
	@Autowired
	private MateriaRepository materiaRepository;
	
	@BeforeEach
	public void init() {
		this.montaBaseDeMaterias();
		this.montaBaseDeCurso();
	}
	
	@AfterEach
	public void finish() {
		this.materiaRepository.deleteAll();
		this.cursoRespository.deleteAll();
	}
	
	private void montaBaseDeMaterias() {
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
	
	private void montaBaseDeCurso() {
		
		List<MateriaEntity> materias = this.materiaRepository.findAll();
		
		CursoEntity c1 = new CursoEntity();
		c1.setCodigoCurso("cc10");
		c1.setNome("Ciencia da Computação");
		c1.setMaterias(materias);
		
		this.cursoRespository.save(c1);
	}
	
	
	
	

}
