package com.rasmoo.cliente.escola.gradecurricular.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.rasmoo.cliente.escola.gradecurricular.controller.MateriaController;
import com.rasmoo.cliente.escola.gradecurricular.dto.MateriaDTO;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.exception.MateriaException;
import com.rasmoo.cliente.escola.gradecurricular.repository.MateriaRepository;
import com.rasmoo.cliente.escola.gradecurricular.service.MateriaService;

@CacheConfig(cacheNames = {"materia"})
@Service
public class MateriaServiceImpl implements MateriaService {

	private static final String MENSAGEM_ERRO = "Erro interno identificado. Contate o suporte";
	private static final String MATERIA_NAO_ENCONTRADA = "Matéria não encontrada";
	private MateriaRepository materiaRepository;
	private ModelMapper mapper;

	@Autowired
	public MateriaServiceImpl(MateriaRepository materiaRepository) {
		this.mapper = new ModelMapper();
		this.materiaRepository = materiaRepository;
	}

	@Override
	public Boolean atualizar(MateriaDTO materiaDto) {
		try {

			this.listarPorId(materiaDto.getId());

			MateriaEntity materia = this.mapper.map(materiaDto, MateriaEntity.class);

			this.materiaRepository.save(materia);

			return Boolean.TRUE;

		} catch (MateriaException m) {
			throw m;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Boolean excluir(Long id) {
		try {
			this.listarPorId(id);
			this.materiaRepository.deleteById(id);
			return true;
		} catch (MateriaException m) {
			throw m;
		} catch (Exception e) {
			throw e;
		}
	}

	@CachePut(unless = "#result.size()<3")
	@Override
	public List<MateriaDTO> listar() {
		try {
			
			List<MateriaDTO> materiaDto = this.mapper.map(this.materiaRepository.findAll(), new TypeToken<List<MateriaDTO>>() {
			}.getType());
			
			materiaDto.forEach(materia ->{
				materia.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
						.methodOn(MateriaController.class)
						.listaMaterias(materia.getId()))
						.withSelfRel());
			});			

			return materiaDto;
			
		} catch (Exception e) {
			throw new MateriaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@CachePut(key = "#id")
	@Override
	public MateriaDTO listarPorId(Long id) {

		try {
			Optional<MateriaEntity> materiaOptional = materiaRepository.findById(id);

			if (materiaOptional.isPresent()) {
				return this.mapper.map(materiaOptional.get(), MateriaDTO.class);
			}
			throw new MateriaException(MATERIA_NAO_ENCONTRADA, HttpStatus.NOT_FOUND);

		} catch (MateriaException m) {
			throw m;
		} catch (Exception e) {
			throw new MateriaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public Boolean cadastra(MateriaDTO materiaDto) {
		try {
			MateriaEntity materia = this.mapper.map(materiaDto, MateriaEntity.class);

			this.materiaRepository.save(materia);
			return Boolean.TRUE;

		} catch (Exception e) {
			throw new MateriaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<MateriaDTO> buscaPelaHora(Integer horaMinima) {
		try {
			return this.mapper.map(materiaRepository.findByHoraMinima(horaMinima), new TypeToken<List<MateriaDTO>>() {}.getType());
//			throw new MateriaException(MATERIA_NAO_ENCONTRADA, HttpStatus.NOT_FOUND);

		} catch (MateriaException m) {
			throw m;
		} catch (Exception e) {
			throw new MateriaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
