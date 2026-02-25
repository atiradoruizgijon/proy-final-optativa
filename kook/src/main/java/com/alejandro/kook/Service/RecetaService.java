package com.alejandro.kook.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alejandro.kook.Dto.RecetaDTO;
import com.alejandro.kook.Model.Receta;

@Service
public interface RecetaService {
    public void crearReceta(RecetaDTO recetaDTO);
    public void eliminarReceta(Long id);
    public Receta encontrarReceta(Long id);
    public List<Receta> getRecetas();
    public void actualizarReceta(Long id, RecetaDTO recetaDTO);
}
