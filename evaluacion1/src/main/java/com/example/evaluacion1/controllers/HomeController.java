package com.example.evaluacion1.controllers;

import com.example.evaluacion1.entities.*;
import com.example.evaluacion1.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
@Controller
public class HomeController {

    @Autowired
    private UploadService upload;

    @Autowired
    private MarcasRelojService marcasRelojService;
    @Autowired
    private AutorizacionService autorizacionService;
    @Autowired
    private JustificativosService justificativosService;

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private InasistenciaService inasistenciaService;

    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private PlanillaService planillaService;
    @GetMapping("/")
    public String home() {
        return "home";
    }
    @GetMapping("/mostrarPlanilla")
    public String mostrarPlanilla(Model model)  {

        //- getALL (marcasReloj, empleados, justificativos, confirmación horas extra)
        ArrayList<MarcasRelojEntity> marcasReloj =marcasRelojService.obtenerMarcasReloj();
        ArrayList<EmpleadoEntity> empleados=empleadoService.obtenerEmpleados();
        ArrayList<JustificativoEntity> justificativos =justificativosService.obtenerJustificativos();
        ArrayList<AutorizacionEntity> autorizaciones = autorizacionService.obtenerAutorizaciones();

        Date fechaInicial = marcasReloj.get(0).getFechaH();
        String fechaInicialStr = marcasReloj.get(0).getFecha();

        Date fecha = fechaInicial;
        String fechaStr = fechaInicialStr;

        int diaRevisado = fechaInicial.getDate();
        int contador = 0;

        ArrayList<MarcasRelojEntity> marcasPorDia= new ArrayList<>();
        ArrayList<Integer> marcasRevisadas = marcasRelojService.crearMarcasRevisadas(marcasReloj.size());

        for(MarcasRelojEntity m:marcasReloj){
                if(marcasRevisadas.get(Math.toIntExact(m.getId())-1).equals(0)){

                    System.out.println("Marcas revisadas");
                    System.out.println(marcasRevisadas);





                    System.out.println("Dia revisado :" + diaRevisado);
                    //revisar quien faltó el día anterior y dejar registro
                    if(m.getFechaH().getDate() != diaRevisado){
                        System.out.println("cambio de día");
                        ArrayList<InasistenciaEntity> inasistencias = inasistenciaService.marcarInasistencias(marcasPorDia,empleados);

                        for (InasistenciaEntity i:inasistencias){
                            inasistenciaService.guardarInasistencia(i);
                        }

                        marcasPorDia.clear();
                        diaRevisado = m.getFechaH().getDate();
                        fechaStr = m.getFecha();
                    }
                    System.out.println("a");
                    //Asignar Fecha
                    int idMarcaReloj = Math.toIntExact(m.getId());
                    //fecha no revisada
                    if (marcasRevisadas.get(idMarcaReloj-1) == 0){
                        System.out.println("b");
                        MarcasRelojEntity marca_2 = new MarcasRelojEntity();
                        for (MarcasRelojEntity mr:marcasReloj){
                            if(m.getFecha().equals(mr.getFecha())){
                                System.out.println("c");
                                if(m.getRut().equals(mr.getRut())){
                                    System.out.println("d");
                                    Date fecha1 = m.getFechaH();
                                    Date fecha2 = mr.getFechaH();


                                    System.out.println("---------Fecha 1-----");
                                    System.out.println(m.getId());
                                    System.out.println(m.getFechaH().getYear()+1900);//año menos 1900
                                    System.out.println(m.getFechaH().getMonth()+1);
                                    System.out.println( m.getFechaH().getDate());





                                    if (!(fecha2.equals(fecha1))){

                                        System.out.println("Aqui si");
                                        System.out.println(m.getId());
                                        System.out.println(mr.getId());

                                        System.out.println("---------Fecha 2 confirmada-----");
                                        System.out.println(mr.getId());
                                        System.out.println(mr.getFechaH().getYear()+1900);//año menos 1900
                                        System.out.println(mr.getFechaH().getMonth()+1);
                                        System.out.println( mr.getFechaH().getDate());

                                        //Calcular info para planilla con nueva entidad asistencia
                                        AsistenciaEntity as = asistenciaService.crearAsistencia(m,mr);
                                        asistenciaService.guardarAsistencia(as);
                                        //marcar como revisada fecha 1 y 2
                                        int idMarcaReloj_2 = Math.toIntExact(mr.getId());
                                        marcasRevisadas.set(idMarcaReloj_2-1,1);
                                        marcasRevisadas.set(idMarcaReloj-1,1);
                                        marcasPorDia.add(m);
                                    }


                                }
                            }
                        }
                    }
                }
        }


        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        //existen todas las asistencias
        ArrayList<PlanillaEntity> planillas = null;
        try {
            planillas = planillaService.calcularSueldos();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        for(PlanillaEntity p:planillas){
            planillaService.guardarPlanilla(p);
        }


        model.addAttribute("planillas",planillas);
        return "planilla";
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


    @PostMapping("/cargarJustf")
    public String cargaJustificativo(@RequestParam("rut") String rut,@RequestParam("date") String fecha, @RequestParam("justf") MultipartFile file, RedirectAttributes ms){

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

}
