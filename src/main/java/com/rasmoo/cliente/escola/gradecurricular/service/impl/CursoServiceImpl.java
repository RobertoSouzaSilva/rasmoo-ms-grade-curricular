package com.rasmoo.cliente.escola.gradecurricular.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.rasmoo.cliente.escola.gradecurricular.constant.MensagensContant;
import com.rasmoo.cliente.escola.gradecurricular.entity.CursoEntity;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.exception.CursoException;
import com.rasmoo.cliente.escola.gradecurricular.model.CursoModel;
import com.rasmoo.cliente.escola.gradecurricular.repository.CursoRepository;
import com.rasmoo.cliente.escola.gradecurricular.repository.MateriaRepository;
import com.rasmoo.cliente.escola.gradecurricular.service.CursoService;

@Service
public class CursoServiceImpl implements CursoService {

	private static final String CURSO_ERRO = "Erro interno identificado. Contate o suporte";
	private static final String CURSO_NAO_ENCONTRADO = "curso não encontrado";
	private CursoRepository cursoRepository;
	private MateriaRepository materiaRepository;
	private ModelMapper mapper;

	@Autowired
	public CursoServiceImpl(CursoRepository cursoRepository, MateriaRepository materiaRepository) {
		this.materiaRepository = materiaRepository;
		this.cursoRepository = cursoRepository;
		this.mapper = new ModelMapper();
	}

	@Override
	public Boolean cadastrar(CursoModel cursoDTO) {
		try {

			if (cursoDTO.getId() != null) {
				throw new CursoException(HttpStatus.BAD_REQUEST, MensagensContant.ERRO_ID_INFORMADO.getValor());
			}

			
			if (this.cursoRepository.findByCodigoCurso(cursoDTO.getCodigoCurso()) != null) {
				throw new CursoException(HttpStatus.BAD_REQUEST,
						MensagensContant.ERRO_CURSO_CADASTRADO_ANTERIORMENTE.getValor());
			}

			return this.cadastrarOuAtualizar(cursoDTO);
			
		} catch (CursoException m) {
			throw m;
		} catch (Exception e) {
			throw new CursoException(HttpStatus.INTERNAL_SERVER_ERROR, CURSO_ERRO);
		}
	}

	@Override
	public Boolean atualizar(CursoModel cursoDTO) {
		try {

			this.listarPeloId(cursoDTO.getId());
			return this.cadastrarOuAtualizar(cursoDTO);

		} catch (CursoException m) {
			throw m;
		} catch (Exception e) {
			throw new CursoException(HttpStatus.INTERNAL_SERVER_ERROR, CURSO_ERRO);
		}

	}

	@Override
	public CursoModel consultaPorCodigo(String codigoCurso) {
		try {
			return this.mapper.map(this.cursoRepository.findByCodigoCurso(codigoCurso), CursoModel.class);
		} catch (CursoException m) {
			throw m;
		} catch (Exception e) {
			throw new CursoException(HttpStatus.INTERNAL_SERVER_ERROR, CURSO_ERRO);

		}
	}

	@Override
	public List<CursoEntity> listar() {
		
		try {
			return this.cursoRepository.findAll();

//			List<CursoEntity> curso = this.cursoRepository.findAll();
//			return this.mapper.map( curso, new TypeToken<List<CursoDTO>>() {}.getType() );

		} catch (CursoException m) {
			throw m;
		} catch (Exception e) {
			throw new CursoException(HttpStatus.INTERNAL_SERVER_ERROR, CURSO_ERRO);
		}
	}

	@Override
	public Boolean excluir(Long id) {
		try {
			this.listarPeloId(id);
			this.cursoRepository.deleteById(id);
			return Boolean.TRUE;

		} catch (CursoException m) {
			throw m;
		} catch (Exception e) {
			throw new CursoException(HttpStatus.INTERNAL_SERVER_ERROR, CURSO_NAO_ENCONTRADO);

		}
	}

	@Override
	public CursoModel listarPeloId(Long id) {
		try {
			Optional<CursoEntity> curso = this.cursoRepository.findById(id);

			if (curso.isPresent()) {
				return this.mapper.map(curso.get(), CursoModel.class);
			}
		} catch (CursoException m) {
			throw m;
		} catch (Exception e) {
			throw new CursoException(HttpStatus.INTERNAL_SERVER_ERROR, CURSO_NAO_ENCONTRADO);
		}
		return null;
	}

	private Boolean cadastrarOuAtualizar(CursoModel cursoDto) {
		List<MateriaEntity> listMateriaEntity = new ArrayList<>();

		if (!cursoDto.getMaterias().isEmpty()) {

			cursoDto.getMaterias().forEach(materia -> {
				if (this.materiaRepository.findById(materia).isPresent())
					listMateriaEntity.add(this.materiaRepository.findById(materia).get());
			});
		}

		CursoEntity cursoEntity = new CursoEntity();
		if(cursoDto.getId()!=null) {
			cursoEntity.setId(cursoDto.getId());
		}
		cursoEntity.setCodigoCurso(cursoDto.getCodigoCurso());
		cursoEntity.setNome(cursoDto.getNome());
		cursoEntity.setMaterias(listMateriaEntity);

		this.cursoRepository.save(cursoEntity);

		return Boolean.TRUE;
	}

//	private Boolean cadastrarOuAtualizar(CursoDTO cursoDto) {
//		List<MateriaEntity> listMateriaEntity = new ArrayList<>();
//		
//		if (!cursoDto.getMaterias().isEmpty()) {
//			
//			for(Long mat : cursoDto.getMaterias()) {
//				if (this.materiaRepository.findById(mat).isPresent()) {
//					listMateriaEntity.add(this.materiaRepository.findById(mat).get());
//				}
//			}
//		}
//		
//		CursoEntity cursoEntity = new CursoEntity();
//		if(cursoDto.getId()!=null) {
//			cursoEntity.setId(cursoDto.getId());
//		}
//		cursoEntity.setCodigoCurso(cursoDto.getCodigoCurso());
//		cursoEntity.setNome(cursoDto.getNome());
//		cursoEntity.setMaterias(listMateriaEntity);
//		
//		this.cursoRepository.save(cursoEntity);
//		
//		return Boolean.TRUE;
//	}
//	


}
