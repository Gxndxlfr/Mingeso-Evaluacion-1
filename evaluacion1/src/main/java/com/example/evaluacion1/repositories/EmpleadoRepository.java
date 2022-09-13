package com.example.evaluacion1.repositories;

import com.example.evaluacion1.entities.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Long> {
    public EmpleadoEntity findByRut(String rut);

    @Query("select e from EmpleadoEntity e where e.nombre = :nombre")
    EmpleadoEntity findByNameCustomQuery(@Param("nombre") String nombre);

    @Query(value = "select * from empleados as e where e.nombre = :nombre",
            nativeQuery = true)
    EmpleadoEntity findByNameNativeQuery(@Param("nombre") String nombre);

}