package com.alejandro.kook.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.alejandro.kook.Dto.LoginRequest;
import com.alejandro.kook.Dto.LoginResponse;
import com.alejandro.kook.Dto.UsuarioDTO;
import com.alejandro.kook.Model.Usuario;
import com.alejandro.kook.Service.UsuarioServiceImpl;
import com.alejandro.kook.config.JwtTokenProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private UsuarioServiceImpl usuarioService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/auth/register")
    public LoginResponse save(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario user = this.usuarioService.save(usuarioDTO);
        String token = this.jwtTokenProvider.generateToken(user);
        return new LoginResponse(user.getUsername(), user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(), token);
    }
    
    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody LoginRequest loginDTO) {
        //TODO: process POST request
        Authentication authDTO = new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password());
        
        Authentication authentication = this.authenticationManager.authenticate(authDTO);
        Usuario user = (Usuario) authentication.getPrincipal();

        String token = this.jwtTokenProvider.generateToken(authentication);
        // el token que le enviamos al cliente
        return new LoginResponse(user.getUsername(), user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(), token);
    }
    
}
