package com.neversonsilva.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neversonsilva.cursomc.domains.Cliente;
import com.neversonsilva.cursomc.domains.PagamentoComBoleto;
import com.neversonsilva.cursomc.domains.Pedido;
import com.neversonsilva.cursomc.domains.Produto;
import com.neversonsilva.cursomc.domains.enums.EstadoPagamento;
import com.neversonsilva.cursomc.exceptions.ObjectNotFoundException;
import com.neversonsilva.cursomc.repositories.EnderecoRepository;
import com.neversonsilva.cursomc.repositories.ItemPedidoRepository;
import com.neversonsilva.cursomc.repositories.PagamentoRepository;
import com.neversonsilva.cursomc.repositories.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private EmailService emailService;

	public Pedido find(Integer id) {
		Optional<Pedido> cliente = pedidoRepository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo " + Pedido.class.getName()));
	}

	public Pedido insert(Pedido pedido) {
		
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		
		Cliente cliente = clienteService.find(pedido.getCliente().getId());
		pedido.setCliente(cliente);

		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();

			boletoService.preencherPagamentoComBoleto(pagto, pedido.getInstante());

		}
		pedidoRepository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());

		pedido.getItens().forEach(item -> {
			item.setDesconto(0.0);
			Produto produto = produtoService.find(item.getProduto().getId());
			item.setPreco(produto.getPreco());
			item.setProduto(produto);
			item.setPedido(pedido);
			
		});
		itemPedidoRepository.saveAll(pedido.getItens());
		emailService.sendOrderConfirmationEmail(pedido);
		return pedido;
	}

}
