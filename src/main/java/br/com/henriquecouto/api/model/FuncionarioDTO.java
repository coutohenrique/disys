package br.com.henriquecouto.api.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.henriquecouto.domain.model.Cargo;
import br.com.henriquecouto.domain.model.Departamento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioDTO {

	private Long id;

	private String nome;
	private Integer idade;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date dataAniversario;
	private String documento;
	private Cargo cargo;
	private Departamento departamento;
}
