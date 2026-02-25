package com.alejandro.kook.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alejandro.kook.Dto.UsuarioDTO;
import com.alejandro.kook.Model.Imagen;
import com.alejandro.kook.Model.UserAuthority;
import com.alejandro.kook.Model.Usuario;
import com.alejandro.kook.Repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<Usuario> findByUsername(String username) {
        // TODO Auto-generated method stub
        return this.usuarioRepository.findByUsername(username);
    }

    @Override
    public Usuario save(UsuarioDTO usuarioDTO) {
        // TODO Auto-generated method stub
        Usuario user = new Usuario(null, 
            usuarioDTO.getUsername(),
            this.passwordEncoder.encode(usuarioDTO.getPassword1()),
            usuarioDTO.getEmail(),
            List.of(UserAuthority.READ),
            new HashSet<>()
        );
        return this.usuarioRepository.save(user);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        // TODO Auto-generated method stub
        return this.usuarioRepository.findById(id);
    }

    @Override
    public Usuario eliminarUsuario(Long id) {
        // TODO Auto-generated method stub
        Optional<Usuario> usuarioOpt = this.usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            this.usuarioRepository.delete(usuario);
            return usuario;
        } else {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
    }

    @Override
    @Transactional
    public Set<Imagen> encontrarImagenesPorUsuario(Long id) {
        // TODO Auto-generated method stub
        Usuario usuario = this.usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        return usuario.getImagenes();
    }
    
}
