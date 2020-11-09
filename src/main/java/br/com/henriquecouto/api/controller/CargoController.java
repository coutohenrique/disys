package br.com.henriquecouto.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.henriquecouto.api.converter.CargoConverter;
import br.com.henriquecouto.api.model.CargoDTO;
import br.com.henriquecouto.domain.model.Cargo;
import br.com.henriquecouto.domain.service.CargoService;
import io.swagger.annotations.Api;

@Api( tags="Cargos")
@RestController
@RequestMapping(value = "/cargos")
public class CargoController {

	private CargoService cargoService;
	private CargoConverter cargoConverter;

	public CargoController(CargoService cargoService, CargoConverter cargoConverter) {
		super();
		this.cargoService = cargoService;
		this.cargoConverter = cargoConverter;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CargoDTO salvar(@RequestBody CargoDTO cargoDTO) {
		Cargo cargo = cargoService.salvar(cargoConverter.toDomainObject(cargoDTO));

		return cargoConverter.toDTO(cargo);

	}

	@GetMapping("/{cargoId}")
	public ResponseEntity<CargoDTO> buscarPorId(@PathVariable Long cargoId) {

		return cargoService.buscarPorId(cargoId)
				.map(d -> ResponseEntity.ok().body(cargoConverter.toDTO(d)))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public List<CargoDTO> listar() {
		List<Cargo> funcionarios = cargoService.listarTodos();

		return cargoConverter.toCollectionDTO(funcionarios);
	}

	@PutMapping(value = "/{cargoId}")
	public ResponseEntity<CargoDTO> atualizar(@PathVariable("cargoId") Long cargoId,
			@RequestBody @Valid CargoDTO cargoDTO) {

		return cargoService.buscarPorId(cargoId).map(d -> {
			cargoConverter.copyToDomainObject(cargoDTO, d);
			Cargo cargo = cargoService.salvar(d);

			return ResponseEntity.ok().body(cargoConverter.toDTO(cargo));

		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{cargoId}")
	public ResponseEntity<?> delete(@PathVariable Long cargoId) {
		return cargoService.buscarPorId(cargoId).map(d -> {
			cargoService.excluir(cargoId);

			return ResponseEntity.ok().build();

		}).orElse(ResponseEntity.notFound().build());
	}

}
