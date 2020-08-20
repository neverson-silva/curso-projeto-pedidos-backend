package com.neversonsilva.cursomc.resources;

import com.neversonsilva.cursomc.domains.Pedido;
import com.neversonsilva.cursomc.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService pedidoService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable("id") Integer id) {
		
		Pedido pedido =pedidoService.find(id);
		
		return ResponseEntity.ok(pedido);
			
	}


	@Transactional
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido pedido) {

		pedido = pedidoService.insert(pedido);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
											 .path("/{id}")
											 .buildAndExpand(pedido.getId())
											 .toUri();
		return ResponseEntity.created(uri).build();			
	}

	@GetMapping("")
	public ResponseEntity<Page<Pedido>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="instante") String orderBy,
			@RequestParam(value="direction", defaultValue="DESC") String direction
	) {
		Page<Pedido> pedidos = pedidoService.findPage(page, linesPerPage, orderBy, direction);

		return ResponseEntity.ok().body(pedidos);

	}

}
