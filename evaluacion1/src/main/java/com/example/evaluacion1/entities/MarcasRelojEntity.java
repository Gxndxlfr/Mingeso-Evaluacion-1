package com.example.evaluacion1.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private Date fecha;
    private String hora;
    private String rut;

    public void MarcaRelojConstructor(String newFecha, String newHora, String newRut){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.fecha = formato.parse(newFecha);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        this.hora = newHora;
        this.rut=newRut;
    }

    public void setFecha(String newFecha) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.fecha = formato.parse(newFecha);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
