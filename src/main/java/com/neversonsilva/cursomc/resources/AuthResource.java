package com.neversonsilva.cursomc.resources;

import com.neversonsilva.cursomc.dto.EmailDTO;
import com.neversonsilva.cursomc.security.JWTUtil;
import com.neversonsilva.cursomc.security.UserSS;
import com.neversonsilva.cursomc.services.AuthService;
import com.neversonsilva.cursomc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "auth")
public class AuthResource {
    
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService service;

    @PostMapping(value = "refresh_token")
    public ResponseEntity<Void> refreshToken(final HttpServletResponse response) {

        final UserSS user = UserService.authenticated();
        final String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();

    }

    @PostMapping(value = "forgot")
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO emailTDto, HttpServletResponse response) {

        service.sendNewPassword(emailTDto.getEmail());
        
        return ResponseEntity.noContent().build();

    }
}