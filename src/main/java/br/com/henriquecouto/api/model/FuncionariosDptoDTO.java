package br.com.henriquecouto.api.model;

import java.util.ArrayList;
import java.util.List;

import br.com.henriquecouto.domain.model.Departamento;
import br.com.henriquecouto.domain.model.Funcionario;
import br.com.henriquecouto.domain.model.FuncionarioDepartamento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionariosDptoDTO {

	private Departamento departamento;
	private List<Funcionario> funcionariosAtivos;
	
	public FuncionariosDptoDTO(Departamento dpto, List<FuncionarioDepartamento> h) {

		this.departamento = dpto;
			
		for (FuncionarioDepartamento funcDept : h) {

			if (funcionariosAtivos == null) {
				funcionariosAtivos = new ArrayList<>();

				this.funcionariosAtivos.add(funcDept.getFuncionario());
			}
		}
	}
}
