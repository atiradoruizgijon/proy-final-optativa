package com.alejandro.kook.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alejandro.kook.Dto.RecetaDTO;
import com.alejandro.kook.Model.Receta;
import com.alejandro.kook.Repository.RecetaRepository;

@Service
public class RecetaServiceImpl implements RecetaService {

    private final RecetaRepository recetaRepository;

    public RecetaServiceImpl(RecetaRepository recetaRepository) {
        // TODO Auto-generated constructor stub
        this.recetaRepository = recetaRepository;
    }

    @Override
    public void crearReceta(RecetaDTO recetaDTO) {
        // TODO Auto-generated method stub
        Receta nuevaReceta = new Receta();
        nuevaReceta.setTitulo(recetaDTO.getTitulo());
        nuevaReceta.setTexto(recetaDTO.getTexto());
        nuevaReceta.setFecha(recetaDTO.getFecha());
        recetaRepository.save(nuevaReceta);
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
    
}
