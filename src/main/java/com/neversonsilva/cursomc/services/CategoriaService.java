package com.neversonsilva.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neversonsilva.cursomc.domain.Categoria;
import com.neversonsilva.cursomc.repositories.CategoryRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> categoria = categoryRepository.findById(id);
		return categoria.orElse(null);
	}
}
