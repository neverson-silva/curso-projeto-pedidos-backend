package com.neversonsilva.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.neversonsilva.cursomc.domains.Categoria;
import com.neversonsilva.cursomc.domains.Cidade;
import com.neversonsilva.cursomc.domains.Estado;
import com.neversonsilva.cursomc.domains.Produto;
import com.neversonsilva.cursomc.repositories.CategoriaRepository;
import com.neversonsilva.cursomc.repositories.CidadeRepository;
import com.neversonsilva.cursomc.repositories.EstadoRepository;
import com.neversonsilva.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository catRepo;
	
	@Autowired
	private ProdutoRepository prodRepo;
	
	@Autowired
	private CidadeRepository cidaRepo;
	
	@Autowired
	private EstadoRepository estaRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto prod1 = new Produto(null, "Computador", 2000.00);
		Produto prod2 = new Produto(null, "Impressora", 800.00);
		Produto prod3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProdutos().addAll(Arrays.asList(prod2));
		
		prod1.getCategorias().addAll(Arrays.asList(cat1));
		prod2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		prod3.getCategorias().addAll(Arrays.asList(cat1));
		
		
		catRepo.saveAll(Arrays.asList(cat1, cat2));	
		prodRepo.saveAll(Arrays.asList(prod1, prod2, prod3));
		
		Estado est1 = new Estado("Minas Gerais");
		Estado est2 = new Estado("São Paulo");
		
		Cidade c1 = new Cidade("Uberlândia", est1);
		Cidade c2 = new Cidade("São Paulo", est2);
		Cidade c3 = new Cidade("Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estaRepo.saveAll(Arrays.asList(est1, est2));
		cidaRepo.saveAll(Arrays.asList(c1, c2, c3));
		
	}
	
}
