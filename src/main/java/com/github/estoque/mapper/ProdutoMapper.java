package com.github.estoque.mapper;

import com.github.estoque.dto.ProdutoDTO;
import com.github.estoque.entity.ProdutoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface ProdutoMapper {

    ProdutoDTO toDTO(ProdutoEntity produtoEntity);

    List<ProdutoDTO> toDTO(List<ProdutoEntity> produtoEntities);

    ProdutoEntity toEntity(ProdutoDTO produtoDTO);

    @Mapping(target = "id", ignore = true)
    ProdutoEntity toEntity(ProdutoDTO produtoDTO, @MappingTarget ProdutoEntity entity);
}
