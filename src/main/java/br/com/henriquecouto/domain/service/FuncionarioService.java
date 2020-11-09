package br.com.henriquecouto.domain.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import br.com.henriquecouto.api.model.HistoricoFuncDptoDTO;
import br.com.henriquecouto.domain.exception.CargoNaoEncontradoException;
import br.com.henriquecouto.domain.exception.DepartamentoNaoEncontradoException;
import br.com.henriquecouto.domain.model.Cargo;
import br.com.henriquecouto.domain.model.Departamento;
import br.com.henriquecouto.domain.model.Funcionario;
import br.com.henriquecouto.domain.model.FuncionarioDepartamento;
import br.com.henriquecouto.domain.model.enums.StatusFuncionarioDeptoEnum;
import br.com.henriquecouto.domain.repository.CargoRepository;
import br.com.henriquecouto.domain.repository.DepartamentoRepository;
import br.com.henriquecouto.domain.repository.FuncionarioDepartamentoRepository;
import br.com.henriquecouto.domain.repository.FuncionarioRepository;

@Service
public class FuncionarioService {
	
	private static final String MSG_CARGO_NAO_ENCONTRADO = "Cargo informado não Encontrado";
	private static final String MSG_DEPARTAMENTO_NAO_ENCONTRADO = "Departamento informado não Encontrado";
	
	private FuncionarioRepository funcionarioRepository;
	private CargoRepository cargoRepository;
	private DepartamentoRepository departamentoRepository;
	private FuncionarioDepartamentoRepository funcionarioDptoRepository;
	
	public FuncionarioService(FuncionarioRepository funcionarioRepository, 
							  CargoRepository cargoRepository, 
							  DepartamentoRepository departamentoRepository,
							  FuncionarioDepartamentoRepository funcionarioDptoRepository) {
		
		this.funcionarioRepository = funcionarioRepository;
		this.departamentoRepository = departamentoRepository;
		this.cargoRepository = cargoRepository;
		this.funcionarioDptoRepository = funcionarioDptoRepository;
	}

	@Transactional
	public Funcionario salvar(Funcionario funcionario) {
	 	Optional<Cargo> cargoOptional =  cargoRepository.findById(funcionario.getCargo().getId());
	 	
	 	cargoOptional.orElseThrow(
	 			() -> new  CargoNaoEncontradoException(MSG_CARGO_NAO_ENCONTRADO));
	 	
	 	
	 	Optional<Departamento> deptOptional =  departamentoRepository.findById(funcionario.getDepartamento().getId());

	 	deptOptional.orElseThrow(
	 			() -> new DepartamentoNaoEncontradoException(MSG_DEPARTAMENTO_NAO_ENCONTRADO));
	 	
	 	funcionario.setCargo(cargoOptional.get());
	 	funcionario.setDepartamento(deptOptional.get());
	 	
	 	Funcionario func = funcionarioRepository.save(funcionario);
	 	
	 	buildHistoricoDepartamento(func);
	 	
		return func;
	}
	
	@Transactional
	public void excluir(Long funcionarioId) {
		funcionarioRepository.deleteById(funcionarioId);
	}

	public Optional<Funcionario> buscarPorId(Long funcionarioId) {
		return funcionarioRepository.findById(funcionarioId);
	}
	
	public List<Funcionario> listarTodos() {
		return funcionarioRepository.findAll();
	}
	
	public Optional<HistoricoFuncDptoDTO> buscarHistoricoDpto(Long funcionarioId) {

		Optional<Funcionario> funcionario = funcionarioRepository.findById(funcionarioId);

		return Optional.ofNullable(
					funcionario.map(f -> {
						List<FuncionarioDepartamento> h = funcionarioDptoRepository.findByFuncionario(f);
						if (CollectionUtils.isEmpty(h)) {
							return null;
						}
			return new HistoricoFuncDptoDTO(funcionario.get(), h);
		}).orElseGet(() -> {
			return null;
		}));
	}
	
	public void buildHistoricoDepartamento(Funcionario funcionario) {
		
		FuncionarioDepartamento f = funcionarioDptoRepository.findByFuncionarioAndStatus(funcionario.getId(),
																		   								StatusFuncionarioDeptoEnum.ATIVO);
		
		if(f != null) {
			f.setStatus(StatusFuncionarioDeptoEnum.INATIVO);
			
			funcionarioDptoRepository.save(f);
		}
		
		FuncionarioDepartamento funcDpto = new FuncionarioDepartamento();

		funcDpto.setDepartamento(funcionario.getDepartamento());
		funcDpto.setFuncionario(funcionario);
		funcDpto.setStatus(StatusFuncionarioDeptoEnum.ATIVO);

		funcionarioDptoRepository.save(funcDpto);
	}
}
