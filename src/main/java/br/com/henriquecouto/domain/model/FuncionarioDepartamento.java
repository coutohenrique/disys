package br.com.henriquecouto.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.henriquecouto.domain.model.enums.StatusFuncionarioDeptoEnum;
import lombok.Data;

@Data
@Entity
@Table(name = "funcionario_departamento")
public class FuncionarioDepartamento {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "departamento_id", referencedColumnName="departamento_id", nullable = false)
	private Departamento departamento;
	
	@ManyToOne
	@JoinColumn(name = "funcionario_id", referencedColumnName="funcionario_id", nullable = false)
	private Funcionario funcionario;
	
	@Enumerated(EnumType.ORDINAL)
	@Column( name = "status")
	private StatusFuncionarioDeptoEnum status;
	

}
