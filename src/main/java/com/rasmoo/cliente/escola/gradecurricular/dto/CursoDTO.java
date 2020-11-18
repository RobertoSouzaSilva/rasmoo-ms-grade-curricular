package com.rasmoo.cliente.escola.gradecurricular.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CursoDTO{
	
	private Long id;
	
	@NotBlank(message = "Informe o nome do curso")
	@Size(min = 10, max = 30)
	private String nome;
	
	@NotBlank(message = "código deve ser preenchido")
	@Size(min = 2, max = 5)
	private String codigoCurso;
	
	private List<Long> materias;
}
