package com.neversonsilva.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.neversonsilva.cursomc.domains.Categoria;
import com.neversonsilva.cursomc.exceptions.DataIntegrityException;
import com.neversonsilva.cursomc.exceptions.ObjectNotFoundException;
import com.neversonsilva.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoryRepository;
	
	public Categoria find(Integer id) {
		Optional<Categoria> categoria = categoryRepository.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return categoryRepository.save(categoria);
	}

	public Categoria update(Categoria categoria) {
		find(categoria.getId());
		return categoryRepository.save(categoria);
	}

	public void delete(Integer id) {
		find(id);
		try {
			categoryRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
		
	}
}
