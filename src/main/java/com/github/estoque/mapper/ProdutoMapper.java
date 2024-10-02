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

    @Mapping(target = "dataCadastro", ignore = true)
    ProdutoEntity toEntity(ProdutoDTO produtoDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    ProdutoEntity toEntity(ProdutoDTO produtoDTO, @MappingTarget ProdutoEntity entity);
}
