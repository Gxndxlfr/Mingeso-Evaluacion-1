package com.example.evaluacion1.controllers;

import com.example.evaluacion1.entities.MarcasRelojEntity;
import com.example.evaluacion1.repositories.MarcasRelojRepository;
import com.example.evaluacion1.services.MarcasRelojService;
import com.example.evaluacion1.services.UploadService;
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
        ArrayList<MarcasRelojEntity> marcas = upload.importarMarcasReloj(contenido);

        for (MarcasRelojEntity m:marcas){
            marcasRelojService.guardarMarcasReloj(m);
        }
        ms.addFlashAttribute("mensaje", "Archivo guardado correctamente!!");
        return "redirect:/other";
    }
}
