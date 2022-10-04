package com.artificer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.artificer.model.Cerveja;
import com.artificer.repository.helper.cerveja.CervejasQueries;

@Repository
public interface CervejasRepository extends JpaRepository<Cerveja, Long>, CervejasQueries {

	Optional<Cerveja> findBySku(String sku);

}
