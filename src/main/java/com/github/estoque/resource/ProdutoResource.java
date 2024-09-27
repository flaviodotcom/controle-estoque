package com.github.estoque.resource;

import com.github.estoque.dto.ProdutoDTO;
import com.github.estoque.service.ProdutoService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/estoque")
@Authenticated
public class ProdutoResource {
    @Inject
    ProdutoService service;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin", "user"})
    public Response exibirProdutos() {
        List<ProdutoDTO> produtos = service.listAll();
        return Response.ok().entity(produtos).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"admin", "user"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response exibirProdutoPorId(@PathParam("id") Long id) {
        ProdutoDTO produtos = service.findById(id);
        return Response.ok().entity(produtos).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response cadastrarProduto(@Valid ProdutoDTO produtoDTO) {
        service.save(produtoDTO);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response atualizarProduto(@PathParam("id") Long id, @Valid ProdutoDTO produto) {
        service.update(id, produto);
        return Response.noContent().build();
    }

    @GET
    @Path("/vencimento")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin", "user"})
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
    @RolesAllowed({"admin", "user"})
    public Response produtosVencidos() {
        List<ProdutoDTO> produtos = service.produtosVencidos();
        if (produtos.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Nenhum produto vencido em estoque").build();
        }
        return Response.ok().entity(produtos).build();
    }
}
