package com.alejandro.kook.Service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alejandro.kook.Dto.ImagenUploadDTO;

@Service
public interface ImagenService {
    public ResponseEntity<Map> subirImagen(ImagenUploadDTO imagenUploadDTO);
}
