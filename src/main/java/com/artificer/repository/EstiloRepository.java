package com.artificer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.artificer.model.Estilo;

@Repository
public interface EstiloRepository extends JpaRepository<Estilo, Long> {

}
