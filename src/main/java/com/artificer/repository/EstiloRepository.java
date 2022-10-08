package com.artificer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.artificer.model.Estilo;
import com.artificer.repository.helper.estilo.EstilosQueries;

@Repository
public interface EstiloRepository extends JpaRepository<Estilo, Long>, EstilosQueries {

	Optional<Estilo> findByNome(String nome);
}
