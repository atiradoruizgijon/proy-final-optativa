package com.alejandro.kook.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.alejandro.kook.Dto.ImagenUploadDTO;
import com.alejandro.kook.Service.ImagenService;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class ImagenController {
    @Autowired
    private ImagenService imagenService;

    @PostMapping("/subirImagen")
    public ResponseEntity<Map> subirImagen(ImagenUploadDTO imagenUploadDTO) {
        //TODO: process POST request
        try {
            return imagenService.subirImagen(imagenUploadDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
