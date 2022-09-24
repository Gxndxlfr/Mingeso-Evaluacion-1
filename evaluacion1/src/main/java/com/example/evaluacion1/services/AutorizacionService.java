package com.example.evaluacion1.services;

import com.example.evaluacion1.entities.AutorizacionEntity;
import com.example.evaluacion1.repositories.AutorizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorizacionService {

    public AutorizacionEntity crearAut(String rut, String contenido) {
        AutorizacionEntity newAut = new AutorizacionEntity();
        newAut.setRut(rut);
        newAut.setContenido(contenido);
        return newAut;
    }
    @Autowired
    AutorizacionRepository autorizacionRepository;
    public AutorizacionEntity guardarAutorizacion(AutorizacionEntity aut) {return autorizacionRepository.save(aut);}
}
