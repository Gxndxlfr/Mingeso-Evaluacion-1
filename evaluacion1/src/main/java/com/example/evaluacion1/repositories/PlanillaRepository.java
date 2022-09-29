package com.example.evaluacion1.repositories;


import com.example.evaluacion1.entities.PlanillaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanillaRepository extends CrudRepository<PlanillaEntity, Long> {
}
