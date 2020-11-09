package br.com.henriquecouto.domain.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "funcionario")
public class Funcionario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "funcionario_id")
	private Long id;
	
	@Column(name = "funcionario_name")
	private String nome;
	
	@Column(name = "funcionario_age")
	private Integer idade;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "funcionario_birthday")
	private Date dataAniversario;
	
	@Column(name = "funcionario_documento")
	private String documento;
	
	@ManyToOne
	@JoinColumn(name = "cargo_id", referencedColumnName="cargo_id", nullable = false)
	private Cargo cargo;
	
	@JsonInclude(Include.NON_NULL)
	@Transient
	private Departamento departamento;
	
}
