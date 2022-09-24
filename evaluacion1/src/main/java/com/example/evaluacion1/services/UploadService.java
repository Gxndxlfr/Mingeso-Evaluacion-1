package com.example.evaluacion1.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


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
        System.out.println("contenido");
        System.out.println(contenido);
        return contenido;
    }

    public String leer_file(MultipartFile file) {
        System.out.println("try read justf");
        String contenido = null;
        try {
            contenido = new String(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error al leer justf");
        }
        System.out.println("contenido justf");
        System.out.println(contenido);
        return contenido;
    }



}
