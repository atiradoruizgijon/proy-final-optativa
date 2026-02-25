package com.alejandro.kook.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alejandro.kook.Dto.ImagenUploadDTO;
import com.alejandro.kook.Model.Imagen;
import com.alejandro.kook.Repository.ImagenRepository;

@Service
public class ImagenServiceImpl implements ImagenService {
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private ImagenRepository imageRepository;

    @Override
    public ResponseEntity<Map> subirImagen(ImagenUploadDTO imagenUploadDTO) {
        try {
            if (imagenUploadDTO.getName().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            if (imagenUploadDTO.getFile().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            Imagen imagen = new Imagen();
            imagen.setName(imagenUploadDTO.getName());
            imagen.setUrl(cloudinaryService.uploadFile(imagenUploadDTO.getFile(), "folder_1"));
            if (imagen.getUrl() == null) {
                return ResponseEntity.badRequest().build();
            }
            imageRepository.save(imagen);
            return ResponseEntity.ok().body(Map.of("url", imagen.getUrl()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @Override
    public Imagen encontrarImagen(Long id) {
        // TODO Auto-generated method stub
        return imageRepository.findById(id).orElse(null);
    }

    @Override
    public List<Imagen> encontrarImagenesPorIdUsuario(Long idUsuario) {
        // TODO Auto-generated method stub
        return imageRepository.findByUsersId(idUsuario);
    }
    
}
