package com.example.evaluacion1.services;

import com.example.evaluacion1.entities.JustificativoEntity;
import com.example.evaluacion1.repositories.JustificativoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JustificativosService {

    public JustificativoEntity crearJustf(String rut, String contenido) {
        JustificativoEntity newJustf = new JustificativoEntity();
        newJustf.setRut(rut);
        newJustf.setContenido(contenido);
        return newJustf;
    }
    @Autowired
    JustificativoRepository justificativoRepository;
    public JustificativoEntity guardarJustificativo(JustificativoEntity justf) {return justificativoRepository.save(justf);}
}
