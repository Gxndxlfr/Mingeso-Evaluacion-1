package com.example.evaluacion1.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "marcas_reloj")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarcasRelojEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private Date fechaH;
    private String fecha;
    private String rut;


}
