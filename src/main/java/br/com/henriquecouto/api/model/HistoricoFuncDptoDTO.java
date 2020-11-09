package br.com.henriquecouto.api.model;

import java.util.ArrayList;
import java.util.List;

import br.com.henriquecouto.domain.model.Departamento;
import br.com.henriquecouto.domain.model.Funcionario;
import br.com.henriquecouto.domain.model.FuncionarioDepartamento;
import br.com.henriquecouto.domain.model.enums.StatusFuncionarioDeptoEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoricoFuncDptoDTO {

	private Funcionario funcionario;
	private List<Departamento> historicoDepartamentos;
	

	public HistoricoFuncDptoDTO(Funcionario funcionario, List<FuncionarioDepartamento> departamentoList) {

		this.funcionario = funcionario;

		for (FuncionarioDepartamento funcDept : departamentoList) {

			if (funcDept.getStatus().equals(StatusFuncionarioDeptoEnum.ATIVO)) {
				this.funcionario.setDepartamento(funcDept.getDepartamento());
			}

			if(historicoDepartamentos== null) {
				historicoDepartamentos = new ArrayList<>();
			}
			
			this.historicoDepartamentos.add(funcDept.getDepartamento());
		}
	}
}
