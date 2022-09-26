package com.example.evaluacion1.controllers;

import com.example.evaluacion1.entities.AutorizacionEntity;
import com.example.evaluacion1.entities.EmpleadoEntity;
import com.example.evaluacion1.entities.JustificativoEntity;
import com.example.evaluacion1.entities.MarcasRelojEntity;
import com.example.evaluacion1.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
public class HomeController {

    @Autowired
    private UploadService upload;

    @Autowired
    private MarcasRelojService marcasRelojService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/cargar")
    public String carga(@RequestParam("archivos") MultipartFile file, RedirectAttributes ms) {
        String contenido = upload.obtenerContenidoArchivo(file);
        ArrayList<MarcasRelojEntity> marcas = marcasRelojService.importarMarcasReloj(contenido);

        for (MarcasRelojEntity m:marcas){
            marcasRelojService.guardarMarcasReloj(m);
        }
        ms.addFlashAttribute("mensaje", "Archivo guardado correctamente!!");
        return "home";
    }

    @Autowired
    private JustificativosService justificativosService;

    @Autowired
    private EmpleadoService empleadoService;
    @PostMapping("/cargarJustf")
    public String cargaJustificativo(@RequestParam("rut") String rut, @RequestParam("justf") MultipartFile file, RedirectAttributes ms){

        //obtener empleados
        System.out.println("try get all empleados");
        ArrayList<EmpleadoEntity>empleados=empleadoService.obtenerEmpleados();



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
            JustificativoEntity justf = justificativosService.crearJustf(rut, contenido);

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

    @Autowired
    private AutorizacionService autorizacionService;

    @PostMapping("/cargarAut")
    public String cargarAutorizacion(@RequestParam("rutA") String rut, @RequestParam("Aut") MultipartFile file, RedirectAttributes ms){
        //obtener empleados
        System.out.println("-----------------------------\n try get all empleados 2 ");
        ArrayList<EmpleadoEntity>empleados=empleadoService.obtenerEmpleados();

        //verificar rut
        Integer validate_rut = 0;
        System.out.println("search empleado 2");
        for (EmpleadoEntity e:empleados){
            System.out.println(e.getRut());
            if (e.getRut().equals(rut)){
                System.out.println("empleado encontrado 2");
                validate_rut = 1;
            }
        }
        if (validate_rut == 1){
            System.out.println("existe el empleado 2");
            //contenido archivo
            String contenido = upload.leer_file(file);
            //crear entidad
            AutorizacionEntity aut = autorizacionService.crearAut(rut, contenido);

            //guardar entidad
            autorizacionService.guardarAutorizacion(aut);
            ms.addFlashAttribute("mensaje", "Autorizacion horas extra guardada correctamente!!");
        }
        else{
            System.out.println(" NO existe el empleado");
            ms.addFlashAttribute("mensaje", "Empleado no existe");

        }
        return "home";
    }
    /*@GetMapping("/calcularPlanilla")
    public String calcularPlanilla() {

    }*/
}
