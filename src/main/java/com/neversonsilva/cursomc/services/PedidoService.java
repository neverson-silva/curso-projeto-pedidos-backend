package com.neversonsilva.cursomc.services;

import java.util.Date;
import java.util.Optional;

import com.neversonsilva.cursomc.domains.Cliente;
import com.neversonsilva.cursomc.domains.PagamentoComBoleto;
import com.neversonsilva.cursomc.domains.Pedido;
import com.neversonsilva.cursomc.domains.Produto;
import com.neversonsilva.cursomc.domains.enums.EstadoPagamento;
import com.neversonsilva.cursomc.exceptions.AuthorizationException;
import com.neversonsilva.cursomc.exceptions.ObjectNotFoundException;
import com.neversonsilva.cursomc.repositories.ItemPedidoRepository;
import com.neversonsilva.cursomc.repositories.PagamentoRepository;
import com.neversonsilva.cursomc.repositories.PedidoRepository;
import com.neversonsilva.cursomc.security.UserSS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

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
		emailService.sendOrderConfirmationHtmlEmail(pedido);
		return pedido;
	}

	public Page<Pedido> findPage(Integer page, Integer linesPage, String orderBy, String direction) {
		
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		Cliente cliente = clienteService.find(user.getId());

		PageRequest pageRequest = PageRequest.of(page, linesPage, Direction.valueOf(direction), orderBy);
		
		return pedidoRepository.findByCliente(cliente, pageRequest);
	}

}
