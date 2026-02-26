package com.alejandro.kook.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alejandro.kook.Dto.ImagenUploadDTO;
import com.alejandro.kook.Model.Imagen;
import com.alejandro.kook.Service.ImagenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@CrossOrigin("*")
public class ImagenController {
    @Autowired
    private ImagenService imagenService;

    @PostMapping("/subir-imagen")
    public ResponseEntity<Map> subirImagen(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name) {
        try {
            ImagenUploadDTO imagenUploadDTO = new ImagenUploadDTO();
            imagenUploadDTO.setFile(file);
            imagenUploadDTO.setName(name);
            return imagenService.subirImagen(imagenUploadDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    
    @GetMapping("/imagen")
    public String encontrar(@RequestParam Long id) {
        return imagenService.encontrarImagen(id).getUrl();
    }

    @GetMapping("/imagen-por-usuario")
    public List<Imagen> getImagenesPorUsuario(@RequestParam Long id) {
        return imagenService.encontrarImagenesPorIdUsuario(id);
    }
    
}
