package com.artificer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.artificer.model.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

	// @Query("from Cidade where estado_id = :estadoId")
	List<Cidade> findByEstadoId(Long estadoId);
}
