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
    public Response exibirProdutos() {
        List<ProdutoDTO> produtos = service.listAll();
        return Response.ok().entity(produtos).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response exibirProdutoPorId(@PathParam("id") Long id) {
        ProdutoDTO produtos = service.findById(id);
        return Response.ok().entity(produtos).build();
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
    public Response produtosPertoDoVencimento() {
        List<ProdutoDTO> produtos = service.vencimentoChegando();
        if (produtos.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Nenhum produto com vencimento próximo").build();
        }
        return Response.ok().entity(produtos).build();
    }

    @GET
    @Path("/vencidos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response produtosVencidos() {
        List<ProdutoDTO> produtos = service.produtosVencidos();
        if (produtos.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Nenhum produto vencido em estoque").build();
        }
        return Response.ok().entity(produtos).build();
    }
}
