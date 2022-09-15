package com.artificer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.artificer.model.Estilo;

@Repository
public interface EstiloRepository extends JpaRepository<Estilo, Long> {

	Optional<Estilo> findByNome(String nome);
}
