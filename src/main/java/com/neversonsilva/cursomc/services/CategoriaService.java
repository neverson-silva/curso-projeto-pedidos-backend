package com.neversonsilva.cursomc.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.neversonsilva.cursomc.domains.Categoria;
import com.neversonsilva.cursomc.dto.CategoriaDTO;
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

	public List<CategoriaDTO> findAll() {
		var categorias =  categoryRepository.findAll();
		List<CategoriaDTO> categoriaDTOList = categorias.stream()
														.map(cat -> new CategoriaDTO(cat))
														.collect(Collectors.toList());
		return categoriaDTOList;
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPage, String orderBy, String direction) {
		
		PageRequest pageRequest = PageRequest.of(page, linesPage, Direction.valueOf(direction), orderBy);
		return categoryRepository.findAll(pageRequest);
	}
}
