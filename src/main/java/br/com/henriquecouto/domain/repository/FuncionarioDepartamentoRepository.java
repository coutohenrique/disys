package br.com.henriquecouto.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.henriquecouto.domain.model.Funcionario;
import br.com.henriquecouto.domain.model.FuncionarioDepartamento;
import br.com.henriquecouto.domain.model.enums.StatusFuncionarioDeptoEnum;

@Repository
public interface FuncionarioDepartamentoRepository extends JpaRepository<FuncionarioDepartamento, Long> {

	
	List<FuncionarioDepartamento>  findByFuncionario(Funcionario funcionario); 
	
	
	@Query("from FuncionarioDepartamento where funcionario.id = :funcionarioId and status = :status")
	FuncionarioDepartamento findByFuncionarioAndStatus(@Param("funcionarioId") Long funcionarioId, 
			@Param("status") StatusFuncionarioDeptoEnum status);
	
	
	@Query("from FuncionarioDepartamento where departamento.id = :departamentoId and status = :status")
	List<FuncionarioDepartamento>  findByDepartamentoAndStatus(@Param("departamentoId") Long funcionarioId, 
			@Param("status") StatusFuncionarioDeptoEnum status);
	
}
