package com.example.evaluacion1.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "planilla")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanillaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private String rut;
    private String nombre;
    private String categoria;
    private double yearsService;
    private double SueldoFijo;
    private double bonificacionYears;
    private double pagoHorasExtra;
    private double descuento;
    private double sueldoBruto;
    private double cotizacionProvicional;
    private double cotizacionSalud;
    private double sueldoFinal;
}
