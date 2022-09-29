package com.example.evaluacion1.services;

import com.example.evaluacion1.entities.JustificativoEntity;
import com.example.evaluacion1.repositories.JustificativoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JustificativosService {

    @Autowired
    JustificativoRepository justificativoRepository;
    public ArrayList<JustificativoEntity> obtenerJustificativos(){
        return (ArrayList<JustificativoEntity>) justificativoRepository.findAll();
    }
    public JustificativoEntity crearJustf(String rut,String fecha, String contenido) {
        JustificativoEntity newJustf = new JustificativoEntity();
        newJustf.setRut(rut);
        newJustf.setFecha(fecha);
        newJustf.setContenido(contenido);
        return newJustf;
    }

    public JustificativoEntity guardarJustificativo(JustificativoEntity justf) {return justificativoRepository.save(justf);}

    public JustificativoEntity obtenerJustificativoPorRutYFecha(String rut, String fecha) {
        return justificativoRepository.getbyRutAndFecha(rut,fecha);
    }
}
