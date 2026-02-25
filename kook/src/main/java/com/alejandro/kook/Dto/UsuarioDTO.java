package com.alejandro.kook.Dto;

import java.util.ArrayList;
import java.util.List;

import com.alejandro.kook.Model.UserAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String username;
    private String email;
    private String password1;
    private String password2;
    private List<UserAuthority> authorities = new ArrayList<>();
}
