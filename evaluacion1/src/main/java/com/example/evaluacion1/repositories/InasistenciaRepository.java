package com.example.evaluacion1.repositories;

import com.example.evaluacion1.entities.InasistenciaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InasistenciaRepository extends CrudRepository<InasistenciaEntity, Long> {

}
