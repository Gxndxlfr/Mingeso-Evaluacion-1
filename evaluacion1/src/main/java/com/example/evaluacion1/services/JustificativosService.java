package com.example.evaluacion1.services;

import com.example.evaluacion1.entities.JustificativoEntity;
import com.example.evaluacion1.repositories.JustificativoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JustificativosService {

    @Autowired
    JustificativoRepository justificativoRepository;
    public JustificativoEntity guardarJustificativo(JustificativoEntity justf) {return justificativoRepository.save(justf);}
}
