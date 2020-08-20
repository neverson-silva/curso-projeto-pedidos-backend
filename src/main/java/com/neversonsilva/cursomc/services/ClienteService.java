package com.neversonsilva.cursomc.services;

import com.neversonsilva.cursomc.domains.Cidade;
import com.neversonsilva.cursomc.domains.Cliente;
import com.neversonsilva.cursomc.domains.Endereco;
import com.neversonsilva.cursomc.domains.enums.Perfil;
import com.neversonsilva.cursomc.domains.enums.TipoCliente;
import com.neversonsilva.cursomc.dto.ClienteDTO;
import com.neversonsilva.cursomc.dto.ClienteNewDto;
import com.neversonsilva.cursomc.exceptions.AuthorizationException;
import com.neversonsilva.cursomc.exceptions.DataIntegrityException;
import com.neversonsilva.cursomc.exceptions.ObjectNotFoundException;
import com.neversonsilva.cursomc.repositories.ClienteRepository;
import com.neversonsilva.cursomc.repositories.EnderecoRepository;
import com.neversonsilva.cursomc.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;
		
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private S3Service s3Service;
	
	public Cliente find(Integer id) {
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN)	&& !id.equals(user.getId()) ) {
			throw new AuthorizationException("Acesso negado");
		}
		Optional<Cliente> pedido = clienteRepository.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo " + Cliente.class.getName()));
	}

	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		cliente = clienteRepository.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		return cliente; 
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
		return new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null, null);
	}

	public Cliente fromDto(ClienteNewDto dto) {

		Cliente cliente = new Cliente(
			dto.getNome(), 
			dto.getEmail(), 
			dto.getCpfOuCnpj(), 
			TipoCliente.toEnum(dto.getTipo()),
			bCryptPasswordEncoder.encode(dto.getSenha()));

		Cidade cidade = new Cidade(dto.getCidadeId(), null, null);

		Endereco endereco = new Endereco(
			dto.getLogradouro(),
			dto.getNumero(),
			dto.getComplemento(),
			dto.getBairro(),
			dto.getCep(),
			cliente,
			cidade
		);

		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(dto.getTelefone1());
		
		if (dto.getTelefone2() != null){
			cliente.getTelefones().add(dto.getTelefone2());
		}
		if (dto.getTelefone3() != null){
			cliente.getTelefones().add(dto.getTelefone3());
		}

		return cliente;

	}
	
	private void updateData(Cliente newCliente, Cliente cliente) {
		newCliente.setNome(cliente.getNome());
		newCliente.setEmail(cliente.getEmail());
	}
	
	public URI uploadProfilePicture(MultipartFile mf) {
		return s3Service.uploadFile(mf);
	}
	
	
}
