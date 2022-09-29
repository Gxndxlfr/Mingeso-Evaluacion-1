package com.example.evaluacion1.repositories;

import com.example.evaluacion1.entities.JustificativoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JustificativoRepository extends CrudRepository<JustificativoEntity, Long> {

    @Query(value = "select * from justificativos as j where j.rut = :rut and j.fecha = :fecha",
            nativeQuery = true)
    JustificativoEntity getbyRutAndFecha(@Param("rut") String rut, @Param("fecha") String fecha);


}
