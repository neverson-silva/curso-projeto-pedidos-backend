package com.neversonsilva.cursomc.resources;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.neversonsilva.cursomc.domains.Pedido;
import com.neversonsilva.cursomc.services.PedidoService;


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
		System.out.println(pedido);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
											 .path("/{id}")
											 .buildAndExpand(pedido.getId())
											 .toUri();
		return ResponseEntity.created(uri).build();			
	}

}
