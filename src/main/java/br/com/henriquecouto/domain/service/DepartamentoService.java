package br.com.henriquecouto.domain.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import br.com.henriquecouto.api.model.FuncionariosDptoDTO;
import br.com.henriquecouto.domain.model.Departamento;
import br.com.henriquecouto.domain.model.FuncionarioDepartamento;
import br.com.henriquecouto.domain.model.enums.StatusFuncionarioDeptoEnum;
import br.com.henriquecouto.domain.repository.DepartamentoRepository;
import br.com.henriquecouto.domain.repository.FuncionarioDepartamentoRepository;

@Service
public class DepartamentoService {

	private DepartamentoRepository departamentoRepository;
	private FuncionarioDepartamentoRepository funcionarioDptoRepository;

	public DepartamentoService(DepartamentoRepository departamentoRepository,
								FuncionarioDepartamentoRepository funcionarioDptoRepository) {
		
		this.departamentoRepository = departamentoRepository;
		this.funcionarioDptoRepository = funcionarioDptoRepository;
	}

	@Transactional
	public Departamento salvar(Departamento departamento) {
		return departamentoRepository.save(departamento);
	}

	@Transactional
	public void excluir(Long departamentoId) {
		departamentoRepository.deleteById(departamentoId);
	}

	public Optional<Departamento> buscarPorId(Long departamentoId) {
		return departamentoRepository.findById(departamentoId);
	}

	public List<Departamento> listarTodos() {
		return departamentoRepository.findAll();
	}
	
	public Optional<FuncionariosDptoDTO> buscarFuncionariosDpto(Long departamentoId) {

		Optional<Departamento> departamento = departamentoRepository.findById(departamentoId);

		return Optional.ofNullable(
				departamento.map(d -> {
					List<FuncionarioDepartamento> h = funcionarioDptoRepository.findByDepartamentoAndStatus(d.getId(),
																									 StatusFuncionarioDeptoEnum.ATIVO);
					if (CollectionUtils.isEmpty(h)) {
						return null;
					}
		return new FuncionariosDptoDTO(departamento.get(), h);
	}).orElseGet(() -> {
		return null;
	}));
	}
}
