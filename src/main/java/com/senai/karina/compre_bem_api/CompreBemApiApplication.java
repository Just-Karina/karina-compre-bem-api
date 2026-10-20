package com.senai.karina.compre_bem_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CompreBemApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompreBemApiApplication.class, args);
	}
	
	@Bean
    CommandLineRunner seedProdutos(ProdutoRepository produtoRepository) {
        return args -> {
            /* Só pra botar os 60 itens, preguiça de escrever nomes pra cada 1 ;-; */
            long produtosExistentes = produtoRepository.count();
            if (produtosExistentes >= 60) {
                return;
            }

            int primeiroProduto = (int) produtosExistentes + 1;
            IntStream.rangeClosed(primeiroProduto, 60).forEach(numero -> {
                Produto produto = new Produto();
                produto.setNome(numero == 1 ? "Cadeira de escritório" : "Produto de catálogo " + numero);
                produto.setDescricao("Produto disponível no catálogo Compre Bem");
                produto.setPreco(BigDecimal.valueOf(numero * 10L));
                produto.setCategoria("Geral");
                produto.setQuantidade(10);
                produtoRepository.save(produto);
            });
        };
    }

}
