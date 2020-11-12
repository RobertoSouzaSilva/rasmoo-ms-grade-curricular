package com.rasmoo.cliente.escola.gradecurricular.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "materia")
@Data
@NoArgsConstructor
public class MateriaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonInclude(Include.NON_NULL)
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	
	@JsonInclude(Include.NON_EMPTY)
	private String nome;
	
	private Integer horas;
	
	@Column(name = "codigo_materia")
	private String codigoMateria;
	
	private Integer frequencia;
}
