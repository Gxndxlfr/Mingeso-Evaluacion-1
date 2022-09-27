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
    private InasistenciaService inasisteniaService;
    @GetMapping("/")
    public String home() {
        return "home";
    }
    @GetMapping("/mostrarPlanilla")
    public String mostrarPlanilla() {

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
                if(marcasRevisadas.get(Math.toIntExact(m.getId() - 1)).equals(0)){

                    System.out.println("Marcas revisadas");
                    System.out.println(marcasRevisadas);

                    System.out.println("---------Fecha 1-----");
                    System.out.println(m.getId());
                    System.out.println(m.getFechaH().getYear()+1900);//año menos 1900
                    System.out.println(m.getFechaH().getMonth()+1);
                    System.out.println( m.getFechaH().getDate());



                    System.out.println("Dia revisado :" + diaRevisado);
                    //revisar quien faltó el día anterior y dejar registro
                    if(m.getFechaH().getDate() != diaRevisado){
                        System.out.println("cambio de día");
                        //inasisteniaService.marcarInasistencias(marcasPorDia,empleados);

                        marcasPorDia.clear();
                        diaRevisado = m.getFechaH().getDate();
                        fechaStr = m.getFecha();
                    }

                    //Asignar Fecha
                    int idMarcaReloj = Math.toIntExact(m.getId());
                    //fecha no revisada
                    if (marcasRevisadas.get(idMarcaReloj-1).equals(0)){

                        MarcasRelojEntity marca_2 = new MarcasRelojEntity();
                        for (MarcasRelojEntity mr:marcasReloj){
                            if(m.getFecha().equals(mr.getFecha())){
                                if(m.getRut().equals(mr.getRut())){
                                    if(!(m.getId().equals(mr.getId()))){
                                        System.out.println("---------Fecha 2-----");
                                        System.out.println(mr.getId());
                                        System.out.println(mr.getFechaH().getYear()+1900);//año menos 1900
                                        System.out.println(mr.getFechaH().getMonth()+1);
                                        System.out.println( mr.getFechaH().getDate());

                                        //Calcular info para planilla con nueva entidad asistencia

                                        //marcar como revisada fecha 1 y 2
                                        int idMarcaReloj_2 = Math.toIntExact(mr.getId());
                                        marcasRevisadas.set(idMarcaReloj_2-1,1);
                                        marcasRevisadas.set(idMarcaReloj-1,1);
                                        marcasPorDia.add(m);
                                        break;
                                    }
                                }
                            }
                        }


                        /*System.out.println("---------Fecha a la que buscar fecha comple-----");
                        System.out.println(m.getId());
                        System.out.println(m.getFechaH().getYear()+1900);//año menos 1900
                        System.out.println(m.getFechaH().getMonth()+1);
                        System.out.println( m.getFechaH().getDate());
                        //buscar fecha 2
                        MarcasRelojEntity marca_2 = new MarcasRelojEntity();
                        marca_2 = marcasRelojService.obtenerFechaComplementaria(m);

                        if(marca_2 == null){
                            System.out.println("valor nulo");

                        }

                        System.out.println("---------Fecha 2-----");
                        System.out.println(marca_2.getId());
                        System.out.println(marca_2.getFechaH().getYear()+1900);//año menos 1900
                        System.out.println(marca_2.getFechaH().getMonth()+1);
                        System.out.println( marca_2.getFechaH().getDate());*/

                    }
                }




        }


            //revisar cambio de mes

                //revisar cambio de dia
                    //revisar empleado, ver fecha 1, buscar fecha 2, fecha 2 not found faltó
                    //fecha 2 se busca dentro del mismo dia que fecha 1
                    //var difference = day2.getTime()-day1.getTime();

            //links de interes
            //https://developando.com/blog/java-sumar-restar-horas-dias-fecha/
            //https://www.delftstack.com/es/howto/java/java-subtract-dates/
            //https://www.delftstack.com/es/howto/java/how-to-compare-two-dates-in-java/

            /*1. Identificar cambio de mes
            2. Identificar cambio de día
            3. Comparar fecha de entrada para asistencia del día analizado
            4. No existe, newInasistencia
            5. Si existe, comparar  minutos de diferencia en caso de atrasos, luego comparar fechah salids para horas extra

            6.Sumar día o reasignar día
            7.Contador días asistidos*/



        return "other";
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
