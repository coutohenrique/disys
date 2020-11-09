package br.com.henriquecouto.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.henriquecouto.domain.model.Funcionario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartamentoDTO {
	
	private Long id;
	private String nome;
	private Funcionario chefeDepartamento;
	
	@JsonIgnore
	private List<FuncionarioDTO> funcionarios;
	
	
	public DepartamentoDTO() {
	}
	
}
