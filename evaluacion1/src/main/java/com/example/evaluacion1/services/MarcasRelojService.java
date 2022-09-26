package com.example.evaluacion1.services;

import com.example.evaluacion1.entities.MarcasRelojEntity;
import com.example.evaluacion1.repositories.MarcasRelojRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class MarcasRelojService {

    public String[] separarPorLineas(String contenido){
        String[] newCont = contenido.split("\n");

        return newCont;
    }

    public String[] separarPorPuntoComa(String linea){
        String[] newLinea = linea.split(";");
        System.out.println("new linea");
        System.out.println(newLinea[0]);System.out.println(newLinea[1]);System.out.println(newLinea[2]);
        return newLinea;
    }
    public Date StringtoDate(String fecha, String hora){

        System.out.println("String to Date");
        String newFecha = fecha + " " + hora;
        System.out.println(newFecha);
        Date newDate;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            newDate = formato.parse(newFecha);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return newDate;
    }

    public MarcasRelojEntity crearMarca(String[] datos){
        MarcasRelojEntity newMarca = new MarcasRelojEntity();
        //newMarca.MarcaRelojConstructor(datos[0],datos[1],datos[2]);

        //transformar string a fecha
        Date fecha = StringtoDate(datos[0],datos[1]);
        //set fecha
        newMarca.setFechaH(fecha);
        newMarca.setFecha(datos[0]);
        newMarca.setRut(datos[2]);
        return newMarca;
    }
    public ArrayList<MarcasRelojEntity> importarMarcasReloj(String contenido){

        //leer caracter a caracter
        ArrayList<MarcasRelojEntity> marcas = new ArrayList<>();
        String[] lineas = separarPorLineas(contenido);

        for (String l: lineas){
            System.out.println("linea");
            System.out.println(l);
            String[] datos = separarPorPuntoComa(l);
            MarcasRelojEntity marca = crearMarca(datos);
            marcas.add(marca);
        }
        return marcas;
    }
    @Autowired
    MarcasRelojRepository marcasRelojRepository;

    public MarcasRelojEntity guardarMarcasReloj(MarcasRelojEntity marca){
        return marcasRelojRepository.save(marca);
    }
}