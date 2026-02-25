package com.alejandro.kook.Dto;

import java.util.List;

/**
 * El LoginResponse es lo que le damos al usuario
 * una vez que se ha logueado
 */
public record LoginResponse(String username, List<String> authorities, String token) {
    
}
