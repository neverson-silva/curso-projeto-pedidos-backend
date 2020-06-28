package com.neversonsilva.cursomc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.neversonsilva.cursomc.domains.Categoria;
import com.neversonsilva.cursomc.services.CategoriaService;


@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService catService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable("id") Integer id) {
		
		Categoria categoria = catService.buscar(id);
		
		return ResponseEntity.ok(categoria);
			
	}
	
	@PostMapping("")
	@ResponseBody
	public ResponseEntity<Void> insert(@RequestBody Categoria categoria) {
		
		categoria = catService.insert(categoria);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
											 .path("/{id}")
											 .buildAndExpand(categoria.getId())
											 .toUri();
		return ResponseEntity.created(uri).build();			
	}

}
