package com.neversonsilva.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.neversonsilva.cursomc.domains.Produto;
import com.neversonsilva.cursomc.dto.ProdutoDTO;
import com.neversonsilva.cursomc.repositories.ProdutoRepository;
import com.neversonsilva.cursomc.services.ProdutoService;
import com.neversonsilva.cursomc.services.validation.utils.URL;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService produtoService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> findByOd(@PathVariable("id") Integer id) {
		
		var produto = produtoService.find(id);
		
		return ResponseEntity.ok(produto);			
	}

	@GetMapping()
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome", defaultValue="") String nome,
			@RequestParam(value="categorias", defaultValue="") String categorias,
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction			
			) {		
		List<Integer> ids = URL.decodeIntList(categorias);

		Page<Produto> produtos = produtoService.search(
			URL.decodeParam(nome),
			ids, 
			page, 
			linesPerPage, 
			orderBy, 
			direction);
		Page<ProdutoDTO> listDto = produtos.map(prod -> new ProdutoDTO(prod));
		
		return ResponseEntity.ok().body(listDto);

	}	
	

}
