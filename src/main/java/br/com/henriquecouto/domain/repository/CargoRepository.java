package br.com.henriquecouto.domain.repository;

import org.springframework.stereotype.Repository;

import br.com.henriquecouto.domain.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long>{

}
