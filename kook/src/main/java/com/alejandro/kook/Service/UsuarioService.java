package com.alejandro.kook.Service;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.alejandro.kook.Dto.UsuarioDTO;
import com.alejandro.kook.Model.Imagen;
import com.alejandro.kook.Model.Usuario;

@Service
public interface UsuarioService {
    public Optional<Usuario> findById(Long id);
    public Optional<Usuario> findByUsername(String username);
    public Usuario save(UsuarioDTO usuarioDTO);
    public Usuario eliminarUsuario(Long id);
    public Set<Imagen> encontrarImagenesPorUsuario(Long id);
}
