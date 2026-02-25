package com.alejandro.kook.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    // nuestro filtro de JWT
    @Autowired
    private JwtFilter jwtFilter;
    
    @Bean
    public AuthenticationManager getAuthenticationManager(HttpSecurity http,
                                                        PasswordEncoder passwordEncoder, 
                                                        UserDetailsService userDetailsService) throws Exception {
        // TODO Auto-generated method stub
        
        // AuthenticationManagerBuilder es una clase que nos permite configurar el AuthenticationManager, 
        // que es el componente de Spring Security que se encarga de autenticar a los usuarios. 
        // Lo obtenemos del HttpSecurity, que es la clase que nos permite configurar la seguridad de las rutas 
        // y los endpoints de nuestra aplicación. Luego, configuramos el AuthenticationManagerBuilder para que 
        // use nuestro UserDetailsService y nuestro PasswordEncoder, y finalmente lo construimos y lo devolvemos.
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

        return authenticationManagerBuilder.build();

        // return http.getSharedObject(AuthenticationManagerBuilder.class)
        //     .userDetailsService(userDetailsService)
        //     .passwordEncoder(passwordEncoder)
        //     .and()
        //     .build();
            // La clase nos permite la creacion de objetos de tipo AuthenticationManager,
            // este AuthenticationManager es lo que en Spring hace que se genere una petición de autenticación,
            // esto va a llamar a un servicio que vamos a tener que implementar.
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(authz -> authz
            .requestMatchers("/auth/**").permitAll() // permitimos el acceso a todas las rutas que empiecen por /auth/ sin necesidad de autenticación, porque ahí es donde vamos a tener los endpoints de registro e inicio de sesión.
            .anyRequest().authenticated()); // para cualquier otra ruta, requerimos que el usuario esté autenticado.
        
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Agregamos nuestro filtro de JWT antes del filtro de autenticación de Spring Security, para que se ejecute antes y pueda validar el token JWT en cada petición.
        // tenemos que crear un filtro que evalue el token
        return http.build();
    }
}
