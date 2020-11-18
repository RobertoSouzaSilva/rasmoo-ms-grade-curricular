package com.rasmoo.cliente.escola.gradecurricular.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class MateriaDTO extends RepresentationModel<MateriaDTO>{

	private Long id;
	
	@NotBlank(message = "Informe o nome da matéria")
	private String nome;
	
	@Min(value = 34, message = "permitido o minimo de 34 horas por matéria")
	@Max(value = 120, message = "permitido o máximo de 120 horas por matéria")
	private Integer horas;
	
	@NotBlank(message = "Informe o código da matéria")
	private String codigoMateria;
	
	@Min(value = 1, message = "permitido o minimo de 1 vez ao ano")
	@Max(value = 2, message = "permitido o máximo de 2 vezes ao ano")
	private Integer frequencia;

}
