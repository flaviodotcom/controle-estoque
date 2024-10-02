package com.github.estoque.dto;

import com.github.estoque.entity.ProdutoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendaDTO {
    private Long id;
    private ProdutoEntity produto;
    private Integer quantidade;
    private LocalDate dataVenda;
}
