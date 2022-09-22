package com.example.evaluacion1.repositories;

import com.example.evaluacion1.entities.JustificativoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JustificativoRepository extends CrudRepository<JustificativoEntity, Long> {
}
