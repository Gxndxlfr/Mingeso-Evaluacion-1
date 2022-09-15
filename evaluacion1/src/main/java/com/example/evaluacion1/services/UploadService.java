package com.example.evaluacion1.services;

import com.example.evaluacion1.entities.MarcasRelojEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


@Service
public class UploadService {


    //importar-marcas
    public String obtenerContenidoArchivo(MultipartFile file){
        String contenido = null;
        try {
            contenido = new String(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error al leer archivo 1");
        }
        return contenido;
    }
    public String[] separarPorLineas(String contenido){
        String[] newCont = contenido.split("/n");

        return newCont;
    }

    public String[] separarPorPuntoComa(String linea){
        String[] newLinea = linea.split(";");

        return newLinea;
    }

    public MarcasRelojEntity crearMarca(String[] datos){
        MarcasRelojEntity newMarca = new MarcasRelojEntity();
        newMarca.MarcaRelojConstructor(datos[0],datos[1],datos[2]);
        return newMarca;
    }
    public ArrayList<MarcasRelojEntity> importarMarcasReloj(String contenido){
        ArrayList<MarcasRelojEntity> marcas = new ArrayList<>();
        String[] lineas = separarPorLineas(contenido);

        for (String l: lineas){
            String[] datos = separarPorPuntoComa(l);
            MarcasRelojEntity marca = crearMarca(datos);
            marcas.add(marca);
        }
        return marcas;
    }
}
