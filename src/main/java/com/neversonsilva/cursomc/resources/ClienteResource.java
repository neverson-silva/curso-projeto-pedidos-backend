package com.neversonsilva.cursomc.resources;

import com.neversonsilva.cursomc.domains.Cliente;
import com.neversonsilva.cursomc.dto.ClienteDTO;
import com.neversonsilva.cursomc.dto.ClienteNewDto;
import com.neversonsilva.cursomc.repositories.ClienteRepository;
import com.neversonsilva.cursomc.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired

	private ClienteRepository cl;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable("id") Integer id) {
		
		Cliente cliente = clienteService.find(id);
		
		return ResponseEntity.ok(cliente);
			
	}

	@RequestMapping(value="/email", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@RequestParam("value") String email) {

		Cliente cliente = clienteService.findByEmail(email);

		return ResponseEntity.ok(cliente);

	}
	

	@PostMapping("")
	@ResponseBody
	@Transactional
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDto objDto) {

		
		Cliente cliente = clienteService.fromDto(objDto);

		cliente = clienteService.insert(cliente);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(cliente.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable("id") Integer id, @Valid @RequestBody ClienteDTO dto) {
		Cliente cliente = clienteService.fromDto(dto);
		cliente.setId(id);
		cliente = clienteService.update(cliente);
		return ResponseEntity.noContent().build();

	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		clienteService.delete(id);
		return ResponseEntity.noContent().build();

	}

	@GetMapping("")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<ClienteDTO> clientes = clienteService.findAll();
		return ResponseEntity.ok().body(clientes);

	}

	@GetMapping("/page")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction
	) {
		Page<Cliente> clientes = clienteService.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listDto = clientes.map(cat -> new ClienteDTO(cat));

		return ResponseEntity.ok().body(listDto);

	}

	@PostMapping("picture")
	public ResponseEntity<Void> insertPicture(@RequestParam(name = "file") MultipartFile file) {

		URI uri = clienteService.uploadProfilePicture(file);

		return ResponseEntity.created(uri).build();
	}

}
