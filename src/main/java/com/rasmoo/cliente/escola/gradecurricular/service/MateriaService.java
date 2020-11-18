package com.rasmoo.cliente.escola.gradecurricular.service;

import java.util.List;

import com.rasmoo.cliente.escola.gradecurricular.dto.MateriaDTO;

public interface MateriaService {

	public Boolean atualizar(MateriaDTO materiaDto);
	
	public Boolean excluir(Long id);
	
	public List<MateriaDTO> listar();
	
	public MateriaDTO listarPorId(Long id);
	
	public Boolean cadastra(MateriaDTO materiaDto);
	
	public List<MateriaDTO> buscaPelaHora(Integer horaMinima);
}
