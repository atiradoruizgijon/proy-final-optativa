package com.alejandro.kook.Dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecetaDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String instrucciones;
    private LocalDate fecha;
}
