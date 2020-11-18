package com.rasmoo.cliente.escola.gradecurricular.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rasmoo.cliente.escola.gradecurricular.entity.CursoEntity;

@Repository
public interface CursoRepository extends JpaRepository<CursoEntity, Long>{
	
	@Query(value = "SELECT * from curso where codigo_curso like :codCurso", nativeQuery = true)
	public CursoEntity findByCodigoCurso(@Param("codCurso")String codCurso);

}
