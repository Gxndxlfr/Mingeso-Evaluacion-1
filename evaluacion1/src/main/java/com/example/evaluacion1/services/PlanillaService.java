package com.example.evaluacion1.services;

import com.example.evaluacion1.entities.*;
import com.example.evaluacion1.repositories.PlanillaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class PlanillaService {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private MarcasRelojService marcasRelojService;

    @Autowired
    private InasistenciaService inasistenciaService;

    @Autowired
    private JustificativosService justificativoService;

    @Autowired
    private PlanillaRepository planillaRepository;
    public ArrayList<PlanillaEntity> calcularSueldos() throws ParseException {

        //Recorrer Empleados
        System.out.println("calcular sueldo A");
        ArrayList<EmpleadoEntity> empleados=empleadoService.obtenerEmpleados();

        ArrayList<MarcasRelojEntity> marcasReloj =marcasRelojService.obtenerMarcasReloj();

        int lenMarcas = marcasReloj.size();
        String fechaActual = marcasReloj.get(lenMarcas-1).getFecha();
        System.out.println("ultima fecha: " + fechaActual);

        ArrayList<PlanillaEntity> planillas = new ArrayList<>();
        for(EmpleadoEntity e:empleados){

            System.out.println("calcular sueldo B");
            PlanillaEntity newPlanilla = new PlanillaEntity();

            newPlanilla.setRut(e.getRut());

            String nombre = e.getNombre() + " " + e.getApellidos();
            newPlanilla.setNombre(nombre);

            newPlanilla.setCategoria(e.getCategoria());

            //String fecha acual pasarlo a timestamp
            Date fechaBase;
            SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
            fechaBase = formato.parse(fechaActual);
            long diff = fechaBase.getTime() - e.getFecha_in().getTime();
            int yearsService = (int) (diff/(1000l * 60 * 60 * 24 * 365));

            newPlanilla.setYearsService( yearsService);
            double sueldoF = 0;
            if (e.getCategoria().equals("1")){
                sueldoF = 1700000.0;

            }if (e.getCategoria().equals("2")){
                sueldoF = 1200000.0;
            }if (e.getCategoria().equals("3")){
                sueldoF = 800000.0;
            }


            newPlanilla.setSueldoFijo(sueldoF);


            //pago horas extras

            System.out.println("calcular sueldo C");
            ArrayList<AsistenciaEntity> horasExtra = asistenciaService.obtenerAsistencias();
            System.out.println("horas extra");
            int horasExtrasEmpleado = 0;
            for(AsistenciaEntity he:horasExtra){

                if(he.getHoras() > 10 && he.getRut().equals(e.getRut())){
                    System.out.println(he);
                    horasExtrasEmpleado = horasExtrasEmpleado + (he.getHoras() - 10);
                }
            }
            System.out.println(horasExtrasEmpleado);
            int pagoHorasExtras = 0;
            if (e.getCategoria().equals("1")){
                pagoHorasExtras = 25000*horasExtrasEmpleado;

            }if (e.getCategoria().equals("2")){
                pagoHorasExtras = 20000*horasExtrasEmpleado;
            }if (e.getCategoria().equals("3")){
                pagoHorasExtras = 10000*horasExtrasEmpleado;
            }

            newPlanilla.setPagoHorasExtra(pagoHorasExtras);

            System.out.println("calcular sueldo D");
            //Descuento
            ArrayList<AsistenciaEntity> atrasos = asistenciaService.obtenerAsistencias();
            System.out.println("atrasos");
            double montoDescuento = 0;
            for(AsistenciaEntity a:atrasos){
                int minAtrasos = a.getAtraso();
                if(minAtrasos > 10 && a.getRut().equals(e.getRut())){
                    System.out.println(a);
                    if(minAtrasos > 10 && minAtrasos <= 25){
                        sueldoF =  (sueldoF - (sueldoF*0.01));
                        montoDescuento =  (montoDescuento + (sueldoF*0.01));
                    }if(minAtrasos > 25 && minAtrasos <= 45){
                        sueldoF = (sueldoF - (sueldoF*0.03));
                        montoDescuento =  (montoDescuento + (sueldoF*0.03));
                    }if(minAtrasos > 45 && minAtrasos <= 70){
                        sueldoF = (sueldoF - (sueldoF*0.06));
                        montoDescuento =  (montoDescuento + (sueldoF*0.06));
                    }if(minAtrasos > 70){
                        InasistenciaEntity inasistencia = new InasistenciaEntity();
                        inasistencia.setFecha(a.getFecha());
                        inasistencia.setRut(e.getRut());
                        System.out.println("calcular sueldo D1");
                        inasistenciaService.guardarInasistencia(inasistencia);
                        System.out.println("calcular sueldo D2");
                        JustificativoEntity justf = justificativoService.obtenerJustificativoPorRutYFecha(e.getRut(),a.getFecha());
                        System.out.println("calcular sueldo D3");
                        if(justf == null){
                            sueldoF =  (sueldoF - (sueldoF*0.15));
                            montoDescuento =  (montoDescuento + (sueldoF*0.15));
                        }
                    }
                }
            }
            newPlanilla.setDescuento(montoDescuento);

            System.out.println("calcular sueldo E");
            double bonificacion = 0;
            if (yearsService < 5){
                bonificacion = 0;
            }
            else if (yearsService >= 5 && yearsService < 10){
                bonificacion = sueldoF*0.05;
            }
            else if (yearsService >= 10 && yearsService < 15){
                bonificacion =  sueldoF*0.08;
            }
            else if (yearsService >= 15 && yearsService < 20){
                bonificacion =sueldoF*0.11;
            }
            else if (yearsService >= 20 && yearsService < 25){
                bonificacion =sueldoF*0.14;
            }
            else if (yearsService >= 25){
                bonificacion = sueldoF*0.17;
            }

            newPlanilla.setBonificacionYears(bonificacion);


            double sueldoFinal = sueldoF + bonificacion + pagoHorasExtras;
            newPlanilla.setSueldoBruto(sueldoFinal);
            double valorAux = sueldoFinal;
            sueldoFinal = (sueldoFinal - (valorAux*0.1) - (valorAux*0.08));

            newPlanilla.setCotizacionSalud( (valorAux*0.08));
            newPlanilla.setCotizacionProvicional((valorAux*0.1));

            newPlanilla.setSueldoFinal(sueldoFinal);

            planillas.add(newPlanilla);

            System.out.println("calcular sueldo F");

        }

        System.out.println("calcular sueldo G");

        return planillas;
    }

    public PlanillaEntity guardarPlanilla(PlanillaEntity p) {return planillaRepository.save(p);}
}
