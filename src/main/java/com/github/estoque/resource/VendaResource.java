package com.github.estoque.resource;

import com.github.estoque.dto.VendaDTO;
import com.github.estoque.service.VendaService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/venda")
@Authenticated
public class VendaResource {

    @Inject
    VendaService service;

    @GET
    @RolesAllowed({"admin", "user"})
    public Response exibirVendas() {
        List<VendaDTO> vendas = service.listAll();
        return Response.ok(vendas).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"admin", "user"})
    public Response exibirVendaById(@PathParam("id") Long id) {
        VendaDTO vendaById = service.findById(id);
        return Response.ok(vendaById).build();
    }

    @POST
    @RolesAllowed({"admin", "user"})
    public Response registrarVenda(VendaDTO venda) {
        service.save(venda);
        return Response.status(Response.Status.CREATED).build();
    }

}
