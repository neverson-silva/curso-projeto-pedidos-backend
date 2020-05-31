package com.neversonsilva.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neversonsilva.cursomc.domains.Categoria;
import com.neversonsilva.cursomc.exceptions.ObjectNotFoundException;
import com.neversonsilva.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoryRepository;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> categoria = categoryRepository.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo " + Categoria.class.getName()));
	}
}
