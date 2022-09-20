package com.example.evaluacion1.services;

import com.example.evaluacion1.entities.MarcasRelojEntity;
import com.example.evaluacion1.repositories.MarcasRelojRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MarcasRelojService {

    @Autowired
    MarcasRelojRepository marcasRelojRepository;

    public MarcasRelojEntity guardarMarcasReloj(MarcasRelojEntity marca){
        return marcasRelojRepository.save(marca);
    }
}