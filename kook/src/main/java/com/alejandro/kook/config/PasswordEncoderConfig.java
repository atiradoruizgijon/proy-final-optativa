package com.alejandro.kook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
/**
 * Clase especifica para configurar el PasswordEncoder, que es el componente que se encarga de encriptar
 * las contraseñas de los usuarios antes de guardarlas en la base de datos, y de verificar las contraseñas
 * cuando los usuarios intentan iniciar sesión.
 * En esta clase se puede definir un bean de tipo PasswordEncoder, que es lo que Spring Security va a usar
 */
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
