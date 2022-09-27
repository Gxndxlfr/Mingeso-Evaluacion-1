package com.example.evaluacion1.repositories;


import com.example.evaluacion1.entities.MarcasRelojEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface MarcasRelojRepository extends CrudRepository<MarcasRelojEntity, Long> {


    @Query(value = "select * from marcas_reloj as m where m.id <> :id and m.fecha = :fecha and m.rut = :rut",
            nativeQuery = true)
    MarcasRelojEntity obtenerParejaMarcaReloj(@Param("id") long id,@Param("fecha") String fecha,@Param("rut") String rut);
}
