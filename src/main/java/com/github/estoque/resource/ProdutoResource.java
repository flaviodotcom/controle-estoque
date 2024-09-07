package com.github.estoque.resource;

import com.github.estoque.dto.ProdutoDTO;
import com.github.estoque.service.ProdutoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/estoque")
public class ProdutoResource {
    @Inject
    ProdutoService service;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProdutoDTO> exibirProdutos() {
        return service.listAll();
    }

    @POST
    public Response cadastrarProduto(ProdutoDTO produtoDTO) {
        try {
            service.save(produtoDTO);
            return Response.status(Response.Status.CREATED).build();

        } catch (Exception exception) {
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarProduto(@PathParam("id") Long id, ProdutoDTO produto) {
        service.update(id, produto);
        return Response.noContent().build();
    }

    @GET
    @Path("/vencimento")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProdutoDTO> produtosPertoDoVencimento() {
        return service.vencimentoChegando();
    }
}
