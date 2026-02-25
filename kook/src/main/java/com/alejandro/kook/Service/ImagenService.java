package com.alejandro.kook.Service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alejandro.kook.Dto.ImagenUploadDTO;
import com.alejandro.kook.Model.Imagen;

@Service
public interface ImagenService {
    public ResponseEntity<Map> subirImagen(ImagenUploadDTO imagenUploadDTO);
    public Imagen encontrarImagen(Long id);
    public List<Imagen> encontrarImagenesPorIdUsuario(Long idUsuario);
}
