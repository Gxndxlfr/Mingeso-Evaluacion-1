package com.example.evaluacion1.services;

import com.example.evaluacion1.entities.MarcasRelojEntity;
import com.example.evaluacion1.repositories.MarcasRelojRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


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

    public MarcasRelojEntity crearMarca(String[] datos){
        MarcasRelojEntity newMarca = new MarcasRelojEntity();
        //newMarca.MarcaRelojConstructor(datos[0],datos[1],datos[2]);

        newMarca.setFecha(datos[0]);
        newMarca.setHora(datos[1]);
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