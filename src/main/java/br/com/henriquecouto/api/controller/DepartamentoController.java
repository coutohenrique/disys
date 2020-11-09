package br.com.henriquecouto.api.controller;

import java.util.List;
import java.util.Optional;

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

import br.com.henriquecouto.api.converter.DepartamentoConverter;
import br.com.henriquecouto.api.model.DepartamentoDTO;
import br.com.henriquecouto.api.model.FuncionariosDptoDTO;
import br.com.henriquecouto.domain.model.Departamento;
import br.com.henriquecouto.domain.model.Funcionario;
import br.com.henriquecouto.domain.service.DepartamentoService;
import br.com.henriquecouto.domain.service.FuncionarioService;
import io.swagger.annotations.Api;

@Api(tags="Departamentos")
@RestController
@RequestMapping(value = "/departamentos")
public class DepartamentoController {

	private DepartamentoService departamentoService;
	private DepartamentoConverter departamentoConverter;
	private FuncionarioService funcionarioService;
	
	public DepartamentoController(DepartamentoService departamentoService, 
								  DepartamentoConverter departamentoConverter,
								  FuncionarioService funcionarioService){
		this.departamentoService = departamentoService;
		this.funcionarioService = funcionarioService;
		this.departamentoConverter = departamentoConverter;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DepartamentoDTO salvar(@RequestBody DepartamentoDTO departamentoDTO) {
		Departamento departamento = departamentoService.salvar(departamentoConverter.toDomainObject(departamentoDTO));

		return departamentoConverter.toDTO(departamento);

	}

	@GetMapping("/{departamentoId}")
	public ResponseEntity<DepartamentoDTO> buscarPorId(@PathVariable Long departamentoId) {

		return departamentoService.buscarPorId(departamentoId)
				.map(d -> ResponseEntity.ok().body(departamentoConverter.toDTO(d)))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public List<DepartamentoDTO> listar() {
		List<Departamento> funcionarios = departamentoService.listarTodos();

		return departamentoConverter.toCollectionDTO(funcionarios);
	}

	@PutMapping(value = "/{departamentoId}")
	public ResponseEntity<DepartamentoDTO> atualizar(@PathVariable("departamentoId") Long departamentoId,
			@RequestBody @Valid DepartamentoDTO departamentoDTO) {

		return departamentoService.buscarPorId(departamentoId)
				.map(d -> {
					
					if(departamentoDTO.getChefeDepartamento()!=null) {
						Optional<Funcionario> funcionario = funcionarioService.buscarPorId(departamentoDTO.getChefeDepartamento().getId());
						
						funcionario.ifPresent( f -> d.setChefeDepartamento(f));
					}
					departamentoConverter.copyToDomainObject(departamentoDTO, d);
					Departamento departamento = departamentoService.salvar(d);

			return ResponseEntity.ok().body(departamentoConverter.toDTO(departamento));

		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{departamentoId}")
	public ResponseEntity<?> delete(@PathVariable Long departamentoId) {
		return departamentoService.buscarPorId(departamentoId).map(d -> {
			departamentoService.excluir(departamentoId);

			return ResponseEntity.ok().build();

		}).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/funcionariosDepartamento/{departamentoId}")
	public ResponseEntity<FuncionariosDptoDTO> buscarFuncionariosDpto(@PathVariable Long departamentoId) {

		return departamentoService.buscarFuncionariosDpto(departamentoId)
				.map( funcionarios -> ResponseEntity.ok().body(funcionarios))
				.orElse(ResponseEntity.notFound().build());
	}
}
