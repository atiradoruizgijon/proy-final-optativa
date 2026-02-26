package com.alejandro.kook.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alejandro.kook.Dto.RecetaDTO;
import com.alejandro.kook.Model.Receta;
import com.alejandro.kook.Repository.RecetaRepository;

@Service
public class RecetaServiceImpl implements RecetaService {

    private final RecetaRepository recetaRepository;

    public RecetaServiceImpl(RecetaRepository recetaRepository, ImagenService imagenService) {
        // TODO Auto-generated constructor stub
        this.recetaRepository = recetaRepository;
    }

    @Override
    public Receta crearReceta(RecetaDTO recetaDTO) {
        // TODO Auto-generated method stub
        Receta nuevaReceta = new Receta();

        nuevaReceta.setTitulo(recetaDTO.getTitulo());
        nuevaReceta.setDescripcion(recetaDTO.getDescripcion());
        nuevaReceta.setInstrucciones(recetaDTO.getInstrucciones());
        // Si la fecha es null, establecer la fecha actual
        nuevaReceta.setFecha(recetaDTO.getFecha() != null ? recetaDTO.getFecha() : LocalDate.now());
        nuevaReceta.setImagenUrl(recetaDTO.getImagenUrl());

        return recetaRepository.save(nuevaReceta);
    }

    @Override
    public void eliminarReceta(Long id) {
        // TODO Auto-generated method stub
        recetaRepository.deleteById(id);
    }

    @Override
    public Receta encontrarReceta(Long id) {
        // TODO Auto-generated method stub
        return recetaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Receta> getRecetas() {
        // TODO Auto-generated method stub
        return recetaRepository.findAll();
    }

    @Override
    public void actualizarReceta(Long id, RecetaDTO recetaDTO) {
        // TODO Auto-generated method stub
        Receta recetaExistente = recetaRepository.findById(id).orElse(null);
        if (recetaExistente != null) {
            recetaExistente.setTitulo(recetaDTO.getTitulo());
            recetaExistente.setDescripcion(recetaDTO.getDescripcion());
            recetaExistente.setInstrucciones(recetaDTO.getInstrucciones());
            recetaExistente.setFecha(recetaDTO.getFecha());
            recetaExistente.setImagenUrl(recetaDTO.getImagenUrl());
            recetaRepository.save(recetaExistente);
        }
    }
}
