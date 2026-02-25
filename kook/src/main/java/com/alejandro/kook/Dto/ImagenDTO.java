package com.alejandro.kook.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImagenDTO {
    private Long id;
    private String name;
    private String url;
    private String publicId;
}
