package com.github.estoque.mapper;

import com.github.estoque.dto.ProdutoDTO;
import com.github.estoque.entity.ProdutoEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface ProdutoMapper {

    ProdutoDTO toDTO(ProdutoEntity produtoEntity);

    List<ProdutoDTO> toDTO(List<ProdutoEntity> produtoEntity);

    ProdutoEntity toEntity(ProdutoDTO produtoDTO);
}
