package com.rasmoo.cliente.escola.gradecurricular.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;

@Repository
public interface MateriaRepository extends JpaRepository<MateriaEntity, Long>{
	
	@Query(value = "select * from Materia where horas >= :horaMinima", nativeQuery = true)
	public List<MateriaEntity> findByHoraMinima(@Param("horaMinima")int horaMinima);


	@Query(value = "select * from Materia where codigo_materia = :codigoMateria", nativeQuery = true)
	public MateriaEntity findByCodigo(@Param("codigoMateria")String codigoMateria);
	
}
