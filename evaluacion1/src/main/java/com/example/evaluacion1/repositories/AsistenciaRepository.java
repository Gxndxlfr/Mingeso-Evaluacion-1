package com.example.evaluacion1.repositories;

import com.example.evaluacion1.entities.AsistenciaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsistenciaRepository extends CrudRepository<AsistenciaEntity, Long> {
}
