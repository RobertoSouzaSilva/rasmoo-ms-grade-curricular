package com.rasmoo.cliente.escola.gradecurricular.service;

import java.util.List;

import com.rasmoo.cliente.escola.gradecurricular.entity.CursoEntity;
import com.rasmoo.cliente.escola.gradecurricular.model.CursoModel;

public interface CursoService {
	
	public Boolean cadastrar(CursoModel cursoDTO);
	
	public Boolean atualizar(CursoModel cursoDTO);
	
	public CursoModel consultaPorCodigo(String codigoCurso);
	
	public List<CursoEntity> listar();
	
	public CursoModel listarPeloId(Long id);
	
	public Boolean excluir(Long id);

}
