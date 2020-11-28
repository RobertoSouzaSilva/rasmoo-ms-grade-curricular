package com.rasmoo.cliente.escola.gradecurricular.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.rasmoo.cliente.escola.gradecurricular.constant.MensagensContant;
import com.rasmoo.cliente.escola.gradecurricular.dto.MateriaDTO;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.exception.MateriaException;
import com.rasmoo.cliente.escola.gradecurricular.repository.MateriaRepository;
import com.rasmoo.cliente.escola.gradecurricular.service.impl.MateriaServiceImpl;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class MateriaServiceUnitTest {
	
	@Mock
	private MateriaRepository materiaRepository;
	
	@InjectMocks
	private MateriaServiceImpl materiaService;
	
	private static MateriaEntity materiaEntity;
	
	@BeforeAll
	public static void init() {
		materiaEntity = new MateriaEntity();
		materiaEntity.setId(1l);
		materiaEntity.setCodigoMateria("lp44b");
		materiaEntity.setFrequencia(1);
		materiaEntity.setHoras(64);
		materiaEntity.setNome("Linguagem de programação");
	}
	
	//sucesso	
	
	@Test
	public void testListarSucesso() {
		List<MateriaEntity> materiaList = new ArrayList<>();
		materiaList.add(materiaEntity);
		
		Mockito.when(this.materiaRepository.findAll()).thenReturn(materiaList);
		
		List<MateriaDTO> listaMateriaDto = this.materiaService.listar();
		
		assertNotNull(listaMateriaDto);
		assertEquals("lp44b", listaMateriaDto.get(0).getCodigoMateria());
		assertEquals("/materia/1", listaMateriaDto.get(0).getLinks().getRequiredLink("self").getHref());
		assertEquals(1, listaMateriaDto.size());
		
		Mockito.verify(this.materiaRepository, times(1)).findAll();
		
	}
	
	@Test
	public void testListarHoraMinimaSucesso() {
		List<MateriaEntity> materiaList = new ArrayList<>();
		materiaList.add(materiaEntity);
		
		Mockito.when(this.materiaRepository.findByHoraMinima(64)).thenReturn(materiaList);
		
		List<MateriaDTO> listaMateriaDto = this.materiaService.buscaPelaHora(64);
		
		assertNotNull(listaMateriaDto);
		assertEquals("lp44b", listaMateriaDto.get(0).getCodigoMateria());
		assertEquals(1, listaMateriaDto.size());
		
		Mockito.verify(this.materiaRepository, times(1)).findByHoraMinima(64);
		
	}
	
	@Test
	public void testConsultaPeloIdSucesso() {
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
		MateriaDTO materiaDto = this.materiaService.listarPorId(1L);
		
		assertNotNull(materiaDto);
		assertEquals("lp44b", materiaDto.getCodigoMateria());
		assertEquals(1, materiaDto.getId());
		assertEquals(1, materiaDto.getFrequencia());
		
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
	}
	
	@Test
	public void testCadastroSucesso() {
		
		MateriaDTO materiaDto = new MateriaDTO();
		materiaDto.setCodigoMateria("lp44b");
		materiaDto.setFrequencia(1);
		materiaDto.setHoras(64);
		materiaDto.setNome("Linguagem de programação");
		
		materiaEntity.setId(null);
		
		Mockito.when(this.materiaRepository.save(materiaEntity)).thenReturn(materiaEntity);
		
		Boolean sucesso = this.materiaService.cadastra(materiaDto);
		
		assertNotNull(sucesso);

		Mockito.verify(this.materiaRepository, times(1)).save(materiaEntity);
		
		materiaEntity.setId(1L);

		
	}
	
	@Test
	public void testAtualizaSucesso() {
		
		MateriaDTO materiaDto = new MateriaDTO();
		materiaDto.setId(1L);
		materiaDto.setCodigoMateria("lp44b");
		materiaDto.setFrequencia(1);
		materiaDto.setHoras(64);
		materiaDto.setNome("Linguagem de programação");
		
		
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
		Mockito.when(this.materiaRepository.save(materiaEntity)).thenReturn(materiaEntity);
		
		Boolean sucesso = this.materiaService.atualizar(materiaDto);
		
		assertNotNull(sucesso);

		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
		Mockito.verify(this.materiaRepository, times(1)).save(materiaEntity);
		
	}
	
	@Test
	public void testExcluirSucesso() {
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
		Boolean sucesso = this.materiaService.excluir(1L);
		
		assertNotNull(sucesso);

		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
		Mockito.verify(this.materiaRepository, times(1)).deleteById(1L);
	}
	
	//throw materia exception
	@Test
	public void testAtualizaThrowMateriaException() {
		
		MateriaDTO materiaDto = new MateriaDTO();
		materiaDto.setId(1L);
		materiaDto.setCodigoMateria("lp44b");
		materiaDto.setFrequencia(1);
		materiaDto.setHoras(64);
		materiaDto.setNome("Linguagem de programação");
		
		
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.empty());
		
		
		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, () -> {
			this.materiaService.atualizar(materiaDto);
		}); 
		
		assertEquals(HttpStatus.NOT_FOUND, materiaException.getHttpStatus());
		assertEquals(MensagensContant.ERRO_MATERIA_NAO_ENCONTRADA.getValor(), materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
		Mockito.verify(this.materiaRepository, times(0)).save(materiaEntity);
	}
	
	@Test
	public void testExcluirThrowMateriaException() {
				
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.empty());
		
		
		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, () -> {
			this.materiaService.excluir(1L);
		}); 
		
		assertEquals(HttpStatus.NOT_FOUND, materiaException.getHttpStatus());
		assertEquals(MensagensContant.ERRO_MATERIA_NAO_ENCONTRADA.getValor(), materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
		Mockito.verify(this.materiaRepository, times(0)).deleteById(1L);
	}
	
	@Test
	public void testCadastraComIdThrowMateriaException() {
		
		MateriaDTO materiaDto = new MateriaDTO();
		materiaDto.setId(1l);
		materiaDto.setCodigoMateria("lp44b");
		materiaDto.setFrequencia(1);
		materiaDto.setHoras(64);
		materiaDto.setNome("Linguagem de programação");		
		
		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, () -> {
			this.materiaService.cadastra(materiaDto);
		}); 
		
		assertEquals(HttpStatus.BAD_REQUEST, materiaException.getHttpStatus());
		assertEquals(MensagensContant.ERRO_ID_INFORMADO.getValor(), materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(0)).findByCodigo("lp44b");
		Mockito.verify(this.materiaRepository, times(0)).save(materiaEntity);
	}
	
	@Test
	public void testCadastrarComCodigoExistenteThrowMateriaException() {

		MateriaDTO materiaDto = new MateriaDTO();
		materiaDto.setCodigoMateria("lp44b");
		materiaDto.setFrequencia(1);
		materiaDto.setHoras(64);
		materiaDto.setNome("Linguagem de programação");
		
		Mockito.when(this.materiaRepository.findByCodigo("lp44b")).thenReturn(materiaEntity);

		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, ()->{
			 this.materiaService.cadastra(materiaDto);
		});
		
		assertEquals(HttpStatus.BAD_REQUEST, materiaException.getHttpStatus());
		assertEquals(MensagensContant.ERRO_MATERIA_CADASTRADA_ANTERIORMENTE.getValor(), materiaException.getMessage());

		Mockito.verify(this.materiaRepository, times(1)).findByCodigo("lp44b");
		Mockito.verify(this.materiaRepository, times(0)).save(materiaEntity);

	}
	
	
	//Exception generica
	@Test
	public void testAtualizaThrowException() {
		
		MateriaDTO materiaDto = new MateriaDTO();
		materiaDto.setId(1L);
		materiaDto.setCodigoMateria("lp44b");
		materiaDto.setFrequencia(1);
		materiaDto.setHoras(64);
		materiaDto.setNome("Linguagem de programação");
		
		
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
		Mockito.when(this.materiaRepository.save(materiaEntity)).thenThrow(IllegalStateException.class);
		
		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, () -> {
			this.materiaService.atualizar(materiaDto);
		}); 
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
		assertEquals(MensagensContant.ERRO_GENERICO.getValor(), materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
		Mockito.verify(this.materiaRepository, times(1)).save(materiaEntity);
	}
	
	@Test
	public void testExcluirThrowException() {
		
		MateriaDTO materiaDto = new MateriaDTO();
		materiaDto.setId(1L);
		materiaDto.setCodigoMateria("lp44b");
		materiaDto.setFrequencia(1);
		materiaDto.setHoras(64);
		materiaDto.setNome("Linguagem de programação");
		
		
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
		Mockito.doThrow(IllegalStateException.class).when(this.materiaRepository).deleteById(1L);
		
		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, () -> {
			this.materiaService.excluir(1L);
		}); 
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
		assertEquals(MensagensContant.ERRO_GENERICO.getValor(), materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).deleteById(1L);
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
	}
	
	
}
