package com.example.evaluacion1.services;

import com.example.evaluacion1.entities.AutorizacionEntity;
import com.example.evaluacion1.repositories.AutorizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AutorizacionService {

    @Autowired
    AutorizacionRepository autorizacionRepository;
    public ArrayList<AutorizacionEntity> obtenerAutorizaciones(){
        return (ArrayList<AutorizacionEntity>) autorizacionRepository.findAll();
    }
    public AutorizacionEntity crearAut(String rut, String contenido) {
        AutorizacionEntity newAut = new AutorizacionEntity();
        newAut.setRut(rut);
        newAut.setContenido(contenido);
        return newAut;
    }

    public AutorizacionEntity guardarAutorizacion(AutorizacionEntity aut) {return autorizacionRepository.save(aut);}
}
