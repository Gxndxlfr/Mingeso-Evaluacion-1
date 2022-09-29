package com.example.evaluacion1.services;


import com.example.evaluacion1.entities.EmpleadoEntity;
import com.example.evaluacion1.entities.InasistenciaEntity;
import com.example.evaluacion1.entities.MarcasRelojEntity;
import com.example.evaluacion1.repositories.InasistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class InasistenciaService {
    @Autowired
    InasistenciaRepository inasistenciaRepository;

    public ArrayList<InasistenciaEntity> marcarInasistencias(ArrayList<MarcasRelojEntity> marcasPorDia, ArrayList<EmpleadoEntity> empleados) {

        ArrayList<InasistenciaEntity> inasistencias = new ArrayList<>();
        for(EmpleadoEntity e:empleados){
            String rut = e.getRut();
            String fecha="" ;
            int encontrado = 0;
            System.out.println("Empleado rut:");
            System.out.println(rut);
            for(MarcasRelojEntity m:marcasPorDia){
                System.out.println("rut analizado");
                System.out.println(m.getRut());
                fecha = m.getFecha();
                if (m.getRut() == e.getRut()){
                    System.out.println("Encontrado");
                    encontrado = 1;

                }

                if (m.getRut().equals(e.getRut())){
                    System.out.println("Encontrado 2");
                    encontrado = 1;

                }
                if(m.getRut().compareTo(e.getRut()) == 0){
                    System.out.println("Encontrado 3");
                    encontrado = 1;
                }
            }
            System.out.println("encontrado: " + encontrado);
            if (encontrado == 0){

                InasistenciaEntity in = new InasistenciaEntity();
                in.setRut(rut);
                in.setFecha(fecha);
                inasistencias.add(in);
            }
        }
        return inasistencias;

    }
    public InasistenciaEntity guardarInasistencia(InasistenciaEntity in) {return inasistenciaRepository.save(in);}



}
