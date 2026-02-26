package com.alejandro.kook.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
    public ResponseEntity<?> save(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario user = this.usuarioService.save(usuarioDTO);
            String token = this.jwtTokenProvider.generateToken(user);
            return ResponseEntity.ok(new LoginResponse(user.getUsername(), user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(), token));
        } catch (Exception e) {
            String message = e.getMessage() != null ? e.getMessage() : "";
            if (message.contains("unique") || message.contains("Unique")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario o email ya está en uso");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en el registro: " + message);
        }
    }
    
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginDTO) {
        try {
            Authentication authDTO = new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password());
            Authentication authentication = this.authenticationManager.authenticate(authDTO);
            Usuario user = (Usuario) authentication.getPrincipal();

            String token = this.jwtTokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new LoginResponse(user.getUsername(), user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(), token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }
    
}
