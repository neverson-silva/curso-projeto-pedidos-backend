package com.neversonsilva.cursomc.services;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import com.neversonsilva.cursomc.domains.*;
import com.neversonsilva.cursomc.dto.ItemPedidoDto;
import com.neversonsilva.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neversonsilva.cursomc.domains.enums.EstadoPagamento;
import com.neversonsilva.cursomc.dto.PagamentoDto;
import com.neversonsilva.cursomc.dto.PedidoDTO;
import com.neversonsilva.cursomc.exceptions.ObjectNotFoundException;
import org.springframework.transaction.annotation.Transactional;

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
	ClienteRepository clienteRepository;

	@Autowired
	EnderecoRepository enderecoRepository;

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
			item.setPedido(pedido);
			
		});
		itemPedidoRepository.saveAll(pedido.getItens());

		return pedido;
	}

	@Transactional
	public Pedido fromDto( PedidoDTO pedidoDTO) {

		Cliente cliente = clienteRepository.findById(pedidoDTO.getCliente().getId()).get();
		Endereco enderecoEntrega = enderecoRepository.findById(pedidoDTO.getEnderecoEntrega().getId()).get();
		Pedido pedido = new Pedido();
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.setCliente(cliente);
		pedido.setEnderecoEntrega(enderecoEntrega);

		if (pedidoDTO.getPagamento().getType().equals("pagamentoComCartao")) {
			pedido.setPagamento(newPagamentoComCartao(pedidoDTO, pedido));
		} else {
			pedido.setPagamento(newPagamentoComBoleto(pedido));
		}
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);


		pedidoDTO.getItens().forEach((ItemPedidoDto item) -> {
			Produto produto = produtoService.find( item.getProduto().getId() );
			ItemPedido itemPedido = new ItemPedido(pedido, produto);
			itemPedido.setDesconto(0.0);
			itemPedido.setPreco( produto.getPreco() );
			itemPedido.setQuantidade( item.getQuantidade() );
			pedido.getItens().add(itemPedido);
			
		});

		pedidoRepository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());

		itemPedidoRepository.saveAll(pedido.getItens());

		return pedido;
	}

	private PagamentoComCartao newPagamentoComCartao(PedidoDTO pedidoDTO, Pedido pedido) {

		PagamentoDto pagamentoDto = pedidoDTO.getPagamento();
		PagamentoComCartao pagamento = new PagamentoComCartao();
		pagamento.setId(null);
		pagamento.setNumeroDeParcelas(pagamentoDto.getNumeroDeParcelas());
		pagamento.setPedido(pedido);
		return pagamento;
	}

	private PagamentoComBoleto newPagamentoComBoleto(Pedido pedido) {

		PagamentoComBoleto pagamento = new PagamentoComBoleto();
		pagamento.setId(null);
		boletoService.preencherPagamentoComBoleto(pagamento, pedido.getInstante());
		pagamento.setPedido(pedido);
		return pagamento;
	}
}
