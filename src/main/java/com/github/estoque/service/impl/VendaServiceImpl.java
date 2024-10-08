package com.github.estoque.service.impl;

import com.github.estoque.dto.VendaDTO;
import com.github.estoque.entity.ProdutoEntity;
import com.github.estoque.entity.VendaEntity;
import com.github.estoque.mapper.VendaMapper;
import com.github.estoque.service.VendaService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

import static com.github.estoque.entity.ProdutoEntity.findBy;

@ApplicationScoped
public class VendaServiceImpl implements VendaService {

    @Inject
    VendaMapper mapper;


    @Override
    public List<VendaDTO> listAll() {
        List<VendaEntity> vendas = VendaEntity.listAll();
        return mapper.toDTO(vendas);
    }

    @Override
    public VendaDTO findById(Long id) {
        VendaEntity venda = (VendaEntity) VendaEntity.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Venda não encontrada com o ID: " + id));

        return mapper.toDTO(venda);
    }

    /**
     * Registra a venda no banco de dados.
     * Atualiza o estoque quando o produto é vendido.
     *
     * @param novaVenda Produto a ser vendido.
     */
    @Override
    @Transactional
    public void save(VendaDTO novaVenda) {
        VendaEntity venda = mapper.toEntity(novaVenda);
        ProdutoEntity produtoEmEstoque = findBy(venda.produto.id);

        Integer qntdProduto = produtoEmEstoque.quantidade;
        Integer qntdVenda = venda.quantidade;

        if (qntdProduto < qntdVenda) {
            throw new IllegalArgumentException("A quantidade do produto não está disponível no estoque");
        }

        produtoEmEstoque.setQuantidade(qntdProduto - qntdVenda);
        venda.setProduto(produtoEmEstoque);

        venda.persist();
        produtoEmEstoque.persist();
    }

    @Override
    public List<VendaDTO> filterVendasPorOrdenacao(String order) {
        String ordenacao = order.equalsIgnoreCase("ASC") ? "ASC" : "DESC";
        List<VendaEntity> venda = VendaEntity.find("ORDER BY total " + ordenacao).list();
        return mapper.toDTO(venda);
    }

}
