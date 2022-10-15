package com.artificer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.artificer.model.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

}
