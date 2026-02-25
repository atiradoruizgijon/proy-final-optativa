package com.alejandro.kook.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alejandro.kook.Dto.UsuarioDTO;
import com.alejandro.kook.Model.UserAuthority;
import com.alejandro.kook.Model.Usuario;
import com.alejandro.kook.Repository.UsuarioRepository;

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
            List.of(UserAuthority.READ)
        );
        return this.usuarioRepository.save(user);
    }
    
}
