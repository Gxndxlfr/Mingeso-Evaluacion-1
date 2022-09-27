package com.example.evaluacion1.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "asistencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsistenciaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String rut;
    private Date ingreso;
    private Date salida;
    private int horas;
    private String fecha;
    private int atraso;
}
