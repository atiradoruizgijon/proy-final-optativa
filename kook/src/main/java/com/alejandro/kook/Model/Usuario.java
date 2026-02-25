package com.alejandro.kook.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
// Implementamos UserDetails para que Spring Security pueda usar esta clase como modelo de usuario
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    @Column(unique = true)
    private String email;
    private String password;
    
    // le decimos a JPA que esta lista de roles o permisos se guarde en una tabla separada, y que se cargue de forma eager (inmediata) cuando se cargue el usuario
    @ElementCollection(fetch = FetchType.EAGER)
    // le decimos que lo guarde en la base de datos como un string, no como un número:
    @Enumerated(EnumType.STRING)
    // es un enum o una clase que representa los roles o permisos del usuario
    private List<UserAuthority> authorities = new ArrayList<>(); // Lista de roles o permisos del usuario

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        // convertimos la lista de UserAuthority a una lista de SimpleGrantedAuthority que es lo que Spring Security espera
        // con un stream, mapeamos cada UserAuthority a un SimpleGrantedAuthority usando su método toString() para obtener el nombre del rol o permiso, y luego lo convertimos a una lista
        return this.authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.toString())).toList();
    }
}
