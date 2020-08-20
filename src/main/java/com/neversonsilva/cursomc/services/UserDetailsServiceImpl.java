package com.neversonsilva.cursomc.services;

import com.neversonsilva.cursomc.domains.Cliente;
import com.neversonsilva.cursomc.repositories.ClienteRepository;
import com.neversonsilva.cursomc.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<Cliente> optCliente = clienteRepository.findByEmail(email);
		
		if (optCliente.isEmpty()) {
			throw new UsernameNotFoundException("Credencias com o email: " + email + "inválidas");
		}
		Cliente cliente = optCliente.get();
		return new UserSS(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfils());
		
	}

}
