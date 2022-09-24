package com.example.evaluacion1.repositories;

import com.example.evaluacion1.entities.AutorizacionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AutorizacionRepository extends CrudRepository<AutorizacionEntity, Long> {
}