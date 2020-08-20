package com.neversonsilva.cursomc.services;

import com.neversonsilva.cursomc.exceptions.ObjectNotFoundException;
import com.neversonsilva.cursomc.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {
    
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmailService emailService;

    private Random random = new Random();
    
    public void sendNewPassword(String email) {

        var cliente = clienteRepository.findByEmail(email);

        if (cliente.isEmpty()) {
            throw new ObjectNotFoundException("Email não encontrado");
        }

        String newPassword = newPassword();
        cliente.get().setSenha(newPassword);
        clienteRepository.save(cliente.get());

        emailService.sendNewPasswordEmail(cliente.get(), newPassword);


     }

    private String newPassword() {
        char[] vet = new char[10];
        
        for(int i = 0; i < 10; i++) {
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = random.nextInt(3);

        if (opt == 0) { // numero
            return (char) (random.nextInt(10) + 48);
        } else if (opt == 1) { //letra maiuscula
            return (char) (random.nextInt(26) + 65);
        } else { //letra minuscula
            return (char) (random.nextInt(26) + 97);
        }
    }
}