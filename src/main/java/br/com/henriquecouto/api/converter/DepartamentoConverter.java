package br.com.henriquecouto.api.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.henriquecouto.api.model.DepartamentoDTO;
import br.com.henriquecouto.domain.model.Departamento;

@Component
public class DepartamentoConverter {

	@Autowired
	private ModelMapper modelMapper;
	

	public DepartamentoDTO toDTO(Departamento departamento) {
		return modelMapper.map(departamento, DepartamentoDTO.class);
	}
	
	public Departamento toDomainObject(DepartamentoDTO departamentoDTO) {
		return modelMapper.map(departamentoDTO, Departamento.class);
	}
	
	public List<DepartamentoDTO> toCollectionDTO(List<Departamento> departamentos){
		return departamentos.stream()
				.map(departamento -> toDTO(departamento))
				.collect(Collectors.toList());
	}
	
	public List<Departamento> toCollectionDomainObject(List<DepartamentoDTO> departamentosDTO){
		return departamentosDTO.stream()
				.map(departamentoDTO -> toDomainObject(departamentoDTO))
				.collect(Collectors.toList());
	}
	
	public void copyToDomainObject(DepartamentoDTO departamentoDTO, Departamento departamento) {
		// Para evitar org.hibernate.HibernateException: identifier of an instance of 
		// br.com.henriquecouto.domain.model.Departamento was altered from 1 to null
		
		departamento.setNome(departamentoDTO.getNome());
	}
}
