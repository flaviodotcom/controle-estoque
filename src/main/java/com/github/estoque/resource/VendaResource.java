package com.github.estoque.resource;

import com.github.estoque.dto.VendaDTO;
import com.github.estoque.service.VendaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/venda")
public class VendaResource {

    @Inject
    VendaService service;

    @GET
    public Response exibirVendas() {
        List<VendaDTO> vendas = service.listAll();
        return Response.ok(vendas).build();
    }

    @GET
    @Path("/{id}")
    public Response exibirVendaById(@PathParam("id") Long id) {
        VendaDTO vendaById = service.findById(id);
        return Response.ok(vendaById).build();
    }

    @POST
    public Response registrarVenda(VendaDTO venda) {
        service.save(venda);
        return Response.status(Response.Status.CREATED).build();
    }

}
