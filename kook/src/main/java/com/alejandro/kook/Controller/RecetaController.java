package com.alejandro.kook.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alejandro.kook.Model.Receta;
import com.alejandro.kook.Service.RecetaServiceImpl;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin("*") // Permitir solicitudes desde cualquier origen
@RestController
@RequestMapping("/recetas")
public class RecetaController {

    private final RecetaServiceImpl recetaService;

    // prefer constructor injection for easier testing and immutability
    public RecetaController(RecetaServiceImpl recetaService) {
        this.recetaService = recetaService;
    }

    @GetMapping("")
    public List<Receta> getRecetas() {
        System.out.println("Obteniendo recetas...");
        return recetaService.getRecetas();
    }
    
}
