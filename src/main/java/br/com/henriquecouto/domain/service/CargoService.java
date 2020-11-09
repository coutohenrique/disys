package br.com.henriquecouto.domain.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.henriquecouto.domain.model.Cargo;
import br.com.henriquecouto.domain.repository.CargoRepository;


@Service
public class CargoService {
	
	private CargoRepository cargoRepository;
	
	public CargoService(CargoRepository cargoRepository) {
		this.cargoRepository = cargoRepository;
	}

	@Transactional
	public Cargo salvar(Cargo cargo) {
		return cargoRepository.save(cargo);
	}

	@Transactional
	public void excluir(Long cargoId) {
		 cargoRepository.deleteById(cargoId);
	}
	
	public Optional<Cargo> buscarPorId(Long cargoId) {
		return cargoRepository.findById(cargoId);
	}

	public List<Cargo> listarTodos() {
		return cargoRepository.findAll();
	}
	
}
