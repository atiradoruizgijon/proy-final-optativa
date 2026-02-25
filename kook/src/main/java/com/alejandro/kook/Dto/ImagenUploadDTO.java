package com.alejandro.kook.Dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * DTO para subir una imagen, contiene el nombre de la imagen y el archivo en sí.
 * Diferente de ImagenDTO, este DTO se utiliza para recibir la imagen desde el cliente, mientras que ImagenDTO se utiliza para enviar la imagen al cliente.
 * El campo "file" es de tipo MultipartFile, que es una clase de Spring que representa un archivo cargado en una solicitud HTTP multipart/form-data. Esto permite que el cliente envíe un archivo de imagen al servidor, que luego puede ser procesado y almacenado.
 * @param name El nombre de la imagen.
 * @param file El archivo de la imagen, representado como un MultipartFile.
 */
public class ImagenUploadDTO {
    private String name;
    private MultipartFile file;
}
