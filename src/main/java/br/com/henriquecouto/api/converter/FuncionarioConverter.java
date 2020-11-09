package br.com.henriquecouto.api.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.henriquecouto.api.model.FuncionarioDTO;
import br.com.henriquecouto.domain.model.Funcionario;

@Component
public class FuncionarioConverter {

	@Autowired
	private ModelMapper modelMapper;
	
	public FuncionarioDTO toDTO(Funcionario funcionario) {
		return modelMapper.map(funcionario, FuncionarioDTO.class);
	}
	
	
	public Funcionario toDomainObject(FuncionarioDTO funcionarioDTO) {
		return modelMapper.map(funcionarioDTO, Funcionario.class);
	}
	
	public void copyToDomainObject(FuncionarioDTO funcionarioDTO, Funcionario funcionario) {
		// Para evitar org.hibernate.HibernateException: identifier of an instance of 
		// br.com.henriquecouto.domain.model.Funcionario was altered from 1 to null
		
		funcionario.setNome(funcionarioDTO.getNome());
		funcionario.setIdade(funcionarioDTO.getIdade());
		funcionario.setDocumento(funcionarioDTO.getDocumento());
		funcionario.setDepartamento(funcionarioDTO.getDepartamento());
		funcionario.setDataAniversario(funcionarioDTO.getDataAniversario());
		funcionario.setCargo(funcionarioDTO.getCargo());
	}
	
	public List<FuncionarioDTO> toCollectionDTO(List<Funcionario> funcionarios){
		return funcionarios.stream()
				.map(funcionario -> toDTO(funcionario))
				.collect(Collectors.toList());
	}
	
	public List<Funcionario> toCollectionDomainObject(List<FuncionarioDTO> funcionariosDTO){
		return funcionariosDTO.stream()
				.map(funcionarioDTO -> toDomainObject(funcionarioDTO))
				.collect(Collectors.toList());
	}
}
