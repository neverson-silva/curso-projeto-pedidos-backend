package com.neversonsilva.cursomc.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.neversonsilva.cursomc.domains.Cliente;
import com.neversonsilva.cursomc.dto.ClienteDTO;
import com.neversonsilva.cursomc.exceptions.DataIntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.neversonsilva.cursomc.domains.Cliente;
import com.neversonsilva.cursomc.exceptions.ObjectNotFoundException;
import com.neversonsilva.cursomc.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> pedido = clienteRepository.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo " + Cliente.class.getName()));
	}

	public Cliente update(Cliente cliente) {

		Cliente newCliente = this.find( cliente.getId() );

		updateData(newCliente, cliente);
		
		return clienteRepository.save(newCliente);
	}

	public void delete(Integer id) {
		find(id);
		try {
			clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível porque há entidades relacionadas");
		}

	}

	public List<ClienteDTO> findAll() {
		List<Cliente> clientes =  clienteRepository.findAll();
		List<ClienteDTO> clienteDTOList = clientes.stream()
				.map( cli -> new ClienteDTO( cli) )
				.collect(Collectors.toList());
		return clienteDTOList;
	}

	public Page<Cliente> findPage(Integer page, Integer linesPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPage, Sort.Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}

	public Cliente fromDto(ClienteDTO dto) {
		return new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null);
	}
	
	private void updateData(Cliente newCliente, Cliente cliente) {
		newCliente.setNome(cliente.getNome());
		newCliente.setEmail(cliente.getEmail());
	}
	
	
	
}
