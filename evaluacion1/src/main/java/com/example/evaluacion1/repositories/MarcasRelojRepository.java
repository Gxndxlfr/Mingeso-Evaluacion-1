package com.example.evaluacion1.repositories;


import com.example.evaluacion1.entities.MarcasRelojEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository ;


@Repository
public interface MarcasRelojRepository extends CrudRepository<MarcasRelojEntity, Long> {

}
