package com.alejandro.kook.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alejandro.kook.Dto.RecetaDTO;
import com.alejandro.kook.Model.Receta;
import com.alejandro.kook.Service.RecetaServiceImpl;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



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

    // las llaves de en el getMapping indican que el id es un parámetro de ruta, no de consulta.
    @GetMapping("/{id}")
    public Receta getRecetaPorId(@PathVariable Long id) {
        return recetaService.encontrarReceta(id);
    }
    
    @PostMapping("/crear-receta")
    public Receta crearReceta(@RequestBody RecetaDTO recetaDTO) {
        return recetaService.crearReceta(recetaDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminarReceta(@PathVariable Long id) {
        recetaService.eliminarReceta(id);
    }

    @PutMapping("/{id}")
    public Receta actualizarReceta(@PathVariable Long id, @RequestBody RecetaDTO recetaDTO) {
        recetaService.actualizarReceta(id, recetaDTO);
        return recetaService.encontrarReceta(id);
    }

}
