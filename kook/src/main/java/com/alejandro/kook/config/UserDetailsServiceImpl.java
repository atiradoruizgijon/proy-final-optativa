package com.alejandro.kook.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alejandro.kook.Service.UsuarioServiceImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UsuarioServiceImpl usuarioService;

    public UserDetailsServiceImpl(UsuarioServiceImpl usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        log.debug("loadUserByUsername {}", username);
        // si lo encuentra en la base de datos lo devuelve, si no, lanza una excepcion
        return this.usuarioService.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario " + username + " no encontrado"));
    }
    
}
