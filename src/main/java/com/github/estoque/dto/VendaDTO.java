package com.github.estoque.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.estoque.entity.ProdutoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendaDTO {
    private Long id;
    private ProdutoEntity produto;
    private Integer quantidade;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataVenda;
    private BigDecimal total;
}
