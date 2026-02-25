package com.alejandro.kook.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Extrae el token JWT de la cabecera Authorization de la petición HTTP.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserDetailsService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        String token = this.extractToken(request);

        if (this.tokenProvider.isValidToken(token)) {
            String username = this.tokenProvider.getUsernameFromToken(token);
            UserDetails user = this.userService.loadUserByUsername(username);

            Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        // para que la peticion siga su curso normal, es decir, que llegue al controlador correspondiente, tenemos que llamar al método doFilter del filterChain, pasándole la request y la response.
        filterChain.doFilter(request, response);
    }
    
    private String extractToken(HttpServletRequest request) {
        // TODO Auto-generated method stub
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasLength(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring("Bearer ".length()); // Elimina "Bearer " del inicio del token
        }
        return null;
    }
}
