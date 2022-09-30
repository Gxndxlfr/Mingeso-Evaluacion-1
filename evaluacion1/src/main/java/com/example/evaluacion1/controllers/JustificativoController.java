package com.example.evaluacion1.controllers;


import com.example.evaluacion1.entities.EmpleadoEntity;
import com.example.evaluacion1.entities.JustificativoEntity;
import com.example.evaluacion1.services.EmpleadoService;
import com.example.evaluacion1.services.JustificativosService;
import com.example.evaluacion1.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
public class JustificativoController {

    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private UploadService upload;

    @Autowired
    private JustificativosService justificativosService;

    @PostMapping("/cargarJustf")
    public String cargaJustificativo(@RequestParam("rut") String rut, @RequestParam("date") String fecha, @RequestParam("justf") MultipartFile file, RedirectAttributes ms){

        //obtener empleados
        System.out.println("try get all empleados");
        ArrayList<EmpleadoEntity> empleados=empleadoService.obtenerEmpleados();



        //verificar rut
        Integer validate_rut = 0;
        System.out.println("search empleado");
        for (EmpleadoEntity e:empleados){
            System.out.println(e.getRut());
            if (e.getRut().equals(rut)){
                System.out.println("empleado encontrado");
                validate_rut = 1;
            }
        }
        if (validate_rut == 1){
            System.out.println("existe el empleado");
            //contenido archivo
            String contenido = upload.leer_file(file);
            //crear entidad
            JustificativoEntity justf = justificativosService.crearJustf(rut,fecha, contenido);

            //guardar entidad
            justificativosService.guardarJustificativo(justf);
            ms.addFlashAttribute("mensaje_2", "Justificativo guardado correctamente!!");

        }
        else{
            System.out.println(" NO existe el empleado");
            ms.addFlashAttribute("mensaje_3", "Empleado no existe");

        }
        return "home";
    }
}
