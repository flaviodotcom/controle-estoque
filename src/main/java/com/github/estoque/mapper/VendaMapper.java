package com.github.estoque.mapper;

import com.github.estoque.dto.VendaDTO;
import com.github.estoque.entity.VendaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface VendaMapper {
    VendaDTO toDTO(VendaEntity venda);

    List<VendaDTO> toDTO(List<VendaEntity> vendaEntities);

    VendaEntity toEntity(VendaDTO VendaDTO);

}
