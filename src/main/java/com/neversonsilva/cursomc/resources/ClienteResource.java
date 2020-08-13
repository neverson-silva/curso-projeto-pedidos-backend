package com.neversonsilva.cursomc.resources;

import com.neversonsilva.cursomc.domains.Cliente;
import com.neversonsilva.cursomc.dto.ClienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.neversonsilva.cursomc.domains.Cliente;
import com.neversonsilva.cursomc.services.ClienteService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable("id") Integer id) {
		
		Cliente cliente = clienteService.find(id);
		
		return ResponseEntity.ok(cliente);
			
	}

	/*@PostMapping("")
	@ResponseBody
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteDTO objDto) {

		Cliente categoria = clienteService.fromDto(objDto);
		categoria = clienteService.insert(categoria);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(categoria.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	*/
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable("id") Integer id, @Valid @RequestBody ClienteDTO dto) {
		Cliente categoria = clienteService.fromDto(dto);
		categoria.setId(id);
		categoria = clienteService.update(categoria);
		return ResponseEntity.noContent().build();

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		clienteService.delete(id);
		return ResponseEntity.noContent().build();

	}

	@GetMapping("")
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<ClienteDTO> categorias = clienteService.findAll();
		return ResponseEntity.ok().body(categorias);

	}

	@GetMapping("/page")
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction
	) {
		Page<Cliente> categorias = clienteService.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listDto = categorias.map(cat -> new ClienteDTO(cat));

		return ResponseEntity.ok().body(listDto);

	}

}
