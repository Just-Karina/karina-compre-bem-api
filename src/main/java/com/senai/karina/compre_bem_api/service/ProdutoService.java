package com.senai.karina.compre_bem_api.service;

import com.senai.karina.compre_bem_api.dto.ProdutoRequestDTO;
import com.senai.karina.compre_bem_api.dto.ProdutoResponseDTO;
import com.senai.karina.compre_bem_api.entity.Produto;
import com.senai.karina.compre_bem_api.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public Page<ProdutoResponseDTO> findAll(String busca, Pageable pageable) {
        Page<Produto> produtos = busca == null || busca.isBlank()
                ? produtoRepository.findAll(pageable)
                : produtoRepository.findByNomeContainingIgnoreCase(busca, pageable);

        return produtos.map(ProdutoResponseDTO::toDto);
    }

    public ProdutoResponseDTO findById(UUID id) {
        var produto = produtoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

        return ProdutoResponseDTO.toDto(produto);
    }

    public Page<ProdutoResponseDTO> findByFiltro(String filtro, Pageable pageable){
        return produtoRepository.findByFiltro(filtro, pageable).map(ProdutoResponseDTO::toDto);
    }

    public ProdutoResponseDTO save(ProdutoRequestDTO produto) {
        Produto produtoSalvo = produtoRepository.save(Produto.toEntity(produto));
        return ProdutoResponseDTO.toDto(produtoSalvo);
    }

    public void delete(UUID produtoId) {
        produtoRepository.deleteById(produtoId);
    }

    public ProdutoResponseDTO update(UUID produtoId, ProdutoRequestDTO produto) {
        Produto produtoExistente = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produtoExistente.setNome(produto.nome());
        produtoExistente.setDescricao(produto.descricao());
        produtoExistente.setPreco(produto.preco());
        produtoExistente.setCategoria(produto.categoria());
        produtoExistente.setImagem(produto.imagem());

        Produto produtoAtualizado = produtoRepository.save(produtoExistente);
        return ProdutoResponseDTO.toDto(produtoAtualizado);

    }
}