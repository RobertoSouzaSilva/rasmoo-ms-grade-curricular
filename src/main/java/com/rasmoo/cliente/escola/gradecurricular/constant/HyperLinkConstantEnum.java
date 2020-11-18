package com.rasmoo.cliente.escola.gradecurricular.constant;

import lombok.Getter;

@Getter
public enum HyperLinkConstantEnum {

	ATUALIZAR("UPDATE"),
	EXCLUIR("DELETE"),
	LISTAR("GETALL"),
	CONSULTAR("GET");
	
	private final String valor;

	private HyperLinkConstantEnum(String valor) {
		this.valor = valor;
	}
	
	
}
