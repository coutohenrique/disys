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

import br.com.henriquecouto.api.converter.FuncionarioConverter;
import br.com.henriquecouto.api.model.FuncionarioDTO;
import br.com.henriquecouto.api.model.HistoricoFuncDptoDTO;
import br.com.henriquecouto.domain.exception.CargoNaoEncontradoException;
import br.com.henriquecouto.domain.exception.DepartamentoNaoEncontradoException;
import br.com.henriquecouto.domain.exception.NegocioException;
import br.com.henriquecouto.domain.model.Funcionario;
import br.com.henriquecouto.domain.service.FuncionarioService;
import io.swagger.annotations.Api;

@Api(tags="Funcionarios")
@RestController
@RequestMapping(value = "/funcionarios")
public class FuncionarioController {

	private FuncionarioService funcionarioService;
	private FuncionarioConverter funcionarioConverter;
	
	

	public FuncionarioController(FuncionarioService funcionarioService, FuncionarioConverter funcionarioConverter) {
		this.funcionarioService = funcionarioService;
		this.funcionarioConverter = funcionarioConverter;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FuncionarioDTO salvar(@RequestBody FuncionarioDTO funcionarioDTO) {
		try {
			Funcionario funcionario = funcionarioService.salvar(funcionarioConverter.toDomainObject(funcionarioDTO));
			
			return funcionarioConverter.toDTO(funcionario);
		}catch (CargoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}catch (DepartamentoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@GetMapping("/{funcionarioId}")
	public ResponseEntity<FuncionarioDTO> buscarPorId(@PathVariable Long funcionarioId) {

		return funcionarioService.buscarPorId(funcionarioId)
				.map(f -> ResponseEntity.ok().body(funcionarioConverter.toDTO(f)))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public List<FuncionarioDTO> listar() {
		List<Funcionario> funcionarios = funcionarioService.listarTodos();

		return funcionarioConverter.toCollectionDTO(funcionarios);
	}

	@PutMapping(value = "/{funcionarioId}")
	public ResponseEntity<FuncionarioDTO> atualizar(@PathVariable("funcionarioId") Long funcionarioId,
			@RequestBody @Valid FuncionarioDTO funcionarioDTO) {
		
		return funcionarioService.buscarPorId(funcionarioId)
				.map(f -> {
					funcionarioConverter.copyToDomainObject(funcionarioDTO, f);
					Funcionario funcionario = funcionarioService.salvar(f);
				
					return ResponseEntity.ok().body(funcionarioConverter.toDTO(funcionario));
				
				}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{funcionarioId}")
	public ResponseEntity <?> delete(@PathVariable Long funcionarioId) {
		return funcionarioService.buscarPorId(funcionarioId)
				.map(f -> {
					funcionarioService.excluir(funcionarioId);
				
					return ResponseEntity.ok().build();
				
				}).orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/historicoFuncionario/{funcionarioId}")
	public ResponseEntity<HistoricoFuncDptoDTO> buscarHistoricoDpto(@PathVariable Long funcionarioId) {

		return funcionarioService.buscarHistoricoDpto(funcionarioId)
				.map(historico -> ResponseEntity.ok().body(historico))
				.orElse(ResponseEntity.notFound().build());
	}
}
