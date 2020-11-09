package br.com.henriquecouto.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "departamento")
public class Departamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "departamento_id")
	private Long id;
	
	@Column(name = "departamento_name")
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "departamento_chefe", referencedColumnName="funcionario_id", nullable = true)
	private Funcionario chefeDepartamento;
	
}
