package com.senai.karina.compre_bem_api.repository;

import com.senai.karina.compre_bem_api.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    @Query("SELECT p FROM Produto p WHERE p.nome LIKE %:filtro% OR p.descricao LIKE %:filtro%")
    List<Produto> findByFiltro(String filtro, Pageable pageable);
    
    Page<Produto> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

}
