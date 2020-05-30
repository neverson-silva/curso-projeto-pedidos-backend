package com.neversonsilva.cursomc.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import domain.Categoria;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@GetMapping("")
	public List<Categoria> listar() {	
		
		var cat1 = new Categoria(1, "Informatica");
		var cat2 = new Categoria(2	, "Escritório");
		
		var lista = Arrays.asList(cat1, cat2, 2);
			
		return lista;
	}

}
