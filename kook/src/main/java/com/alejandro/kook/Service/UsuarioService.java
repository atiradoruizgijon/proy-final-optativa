package com.alejandro.kook.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.alejandro.kook.Dto.UsuarioDTO;
import com.alejandro.kook.Model.Usuario;

@Service
public interface UsuarioService {
    public Optional<Usuario> findByUsername(String username);
    public Usuario save(UsuarioDTO usuarioDTO);
}
