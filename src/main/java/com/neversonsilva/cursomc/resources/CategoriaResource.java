package com.neversonsilva.cursomc.resources;

import com.neversonsilva.cursomc.domains.Categoria;
import com.neversonsilva.cursomc.dto.CategoriaDTO;
import com.neversonsilva.cursomc.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService catService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable("id") Integer id) {
		
		Categoria categoria = catService.find(id);
		
		return ResponseEntity.ok(categoria);
			
	}
	
	@PostMapping("")
	@ResponseBody
	@Transactional
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto) {

		Categoria categoria = catService.fromDto(objDto);
		categoria = catService.insert(categoria);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
											 .path("/{id}")
											 .buildAndExpand(categoria.getId())
											 .toUri();
		return ResponseEntity.created(uri).build();			
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Void> update(@PathVariable("id") Integer id, @Valid @RequestBody CategoriaDTO dto) {
		Categoria categoria = catService.fromDto(dto);
		categoria.setId(id);
		categoria = catService.update(categoria);
		return ResponseEntity.noContent().build();

	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		catService.delete(id);
		return ResponseEntity.noContent().build();

	}
	
	@GetMapping("")
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<CategoriaDTO> categorias = catService.findAll();
		return ResponseEntity.ok().body(categorias);

	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction			
			) {
		Page<Categoria> categorias = catService.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> listDto = categorias.map(cat -> new CategoriaDTO(cat));
		
		return ResponseEntity.ok().body(listDto);

	}	
	
	

}
