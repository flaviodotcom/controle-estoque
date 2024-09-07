package com.github.estoque.mapper;

import com.github.estoque.dto.ProdutoDTO;
import com.github.estoque.entity.ProdutoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface ProdutoMapper {

    ProdutoDTO toDTO(ProdutoEntity produtoEntity);

    List<ProdutoDTO> toDTO(List<ProdutoEntity> produtoEntities);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "preco", target = "preco")
    @Mapping(source = "quantidade", target = "quantidade")
    @Mapping(source = "dataValidade", target = "dataValidade")
    @Mapping(source = "dataCadastro", target = "dataCadastro")
    ProdutoEntity toEntity(ProdutoDTO produtoDTO);
}
