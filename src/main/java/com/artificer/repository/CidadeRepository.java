package com.artificer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.artificer.model.Cidade;
import com.artificer.repository.helper.cidade.CidadesQueries;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long>, CidadesQueries {

	List<Cidade> findByEstadoId(Long estadoId);

	Optional<Cidade> findByNomeAndEstadoId(String name, Long id);
}
