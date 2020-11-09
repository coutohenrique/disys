package br.com.henriquecouto.api.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.henriquecouto.api.model.CargoDTO;
import br.com.henriquecouto.domain.model.Cargo;

@Component
public class CargoConverter {


	private ModelMapper modelMapper;
	
	public CargoConverter(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public CargoDTO toDTO(Cargo cargo) {
		return modelMapper.map(cargo, CargoDTO.class);
	}
	
	public Cargo toDomainObject(CargoDTO departamentoDTO) {
		return modelMapper.map(departamentoDTO, Cargo.class);
	}
	
	public List<CargoDTO> toCollectionDTO(List<Cargo> cargos){
		return cargos.stream()
				.map(cargo -> toDTO(cargo))
				.collect(Collectors.toList());
	}
	
	public List<Cargo> toCollectionDomainObject(List<CargoDTO> cargosDTO){
		return cargosDTO.stream()
				.map(cargoDTO -> toDomainObject(cargoDTO))
				.collect(Collectors.toList());
	}
	
	public void copyToDomainObject(CargoDTO cargoDTO, Cargo cargo) {
		// Para evitar org.hibernate.HibernateException: identifier of an instance of 
		// br.com.henriquecouto.domain.model.Departamento was altered from 1 to null
		
		cargo.setNome(cargoDTO.getNome());
	}
}
