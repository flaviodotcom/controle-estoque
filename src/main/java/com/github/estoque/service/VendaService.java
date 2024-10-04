package com.github.estoque.service;

import com.github.estoque.dto.VendaDTO;

import java.util.List;

public interface VendaService {
    List<VendaDTO> listAll();
    VendaDTO findById(Long id);
    void save(VendaDTO novaVenda);
}
