package com.rasmoo.cliente.escola.gradecurricular.service;

import java.util.List;

import com.rasmoo.cliente.escola.gradecurricular.dto.CursoDTO;

public interface CursoService {
	
	public Boolean cadastrar(CursoDTO cursoDTO);
	
	public Boolean atualizar(CursoDTO cursoDTO);
	
	public CursoDTO consultaPorCodigo(String codigoCurso);
	
	public List<CursoDTO> listar();
	
	public CursoDTO listarPeloId(Long id);
	
	public Boolean excluir(Long id);

}
