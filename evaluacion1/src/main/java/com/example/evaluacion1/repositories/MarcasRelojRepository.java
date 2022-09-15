package com.example.evaluacion1.repositories;


import com.example.evaluacion1.entities.MarcasRelojEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcasRelojRepository extends JpaRepository<MarcasRelojEntity, Long> {

}
