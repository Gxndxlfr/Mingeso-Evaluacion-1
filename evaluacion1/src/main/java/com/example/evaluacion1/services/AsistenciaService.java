package com.example.evaluacion1.services;


import com.example.evaluacion1.entities.AsistenciaEntity;
import com.example.evaluacion1.entities.MarcasRelojEntity;
import com.example.evaluacion1.repositories.AsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AsistenciaService {

    @Autowired
    AsistenciaRepository asistenciaRepository;

    public AsistenciaEntity crearAsistencia(MarcasRelojEntity m, MarcasRelojEntity mr) {
        System.out.println("asistencia");
        AsistenciaEntity newAs = new AsistenciaEntity();
        newAs.setRut(m.getRut());
        newAs.setIngreso(m.getFechaH());
        newAs.setSalida(mr.getFechaH());
        newAs.setFecha(m.getFecha());

        long diff = m.getFechaH().getTime()-mr.getFechaH().getTime();
        var horas = diff/(1000*60*60);
        System.out.println(horas);
        newAs.setHoras(Math.toIntExact(horas));

        String newFecha = m.getFecha() + " " + "08:00";
        Date fechaBase;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            fechaBase = formato.parse(newFecha);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        diff = m.getFechaH().getTime() - fechaBase.getTime();

        var atraso = diff/(1000*60);
        System.out.println(atraso);
        newAs.setAtraso(Math.toIntExact(atraso));

        return newAs;
    }

    public AsistenciaEntity guardarAsistencia(AsistenciaEntity as) { return asistenciaRepository.save(as);
    }
}
