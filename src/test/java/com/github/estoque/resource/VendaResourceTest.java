package com.github.estoque.resource;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
@TestHTTPEndpoint(VendaResource.class)
class VendaResourceTest {

    @Test
    @TestSecurity(authorizationEnabled = false)
    void testExibirVendas() {
        given()
                .when()
                .get()
                .then().statusCode(200)
                .body("isEmpty()", is(false));
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    void testExibirVendasPorId() {
        given()
                .when()
                .get("/403")
                .then().statusCode(200)
                .body("id", is(403))
                .body("produto", notNullValue())
                .body("quantidade", is(1))
                .body("dataVenda", notNullValue());
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    void testRegistrarVenda() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(generateVendaJson(1, 203))
                .when().post()
                .then().statusCode(201);
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    void testNaoRegistrarVendaQuandoOIdDoProdutoNaoEhEncontrado() {
        given()
        .when()
                .contentType(ContentType.JSON)
                .body(generateVendaJson(1, 888))
                .when().post()
                .then().statusCode(404)
                .body("message", equalTo("Produto não encontrado"))
                .body("status", is(404))
                .body("title", equalTo("Produto não encontrado!"));
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    void testNaoRegistrarVendaComQuantidadeInvalida() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(generateVendaJson(-1, 204))
                .when().post()
                .then().statusCode(422)
                .body("message", equalTo("A quantidade de produtos não pode ser negativa"))
                .body("status", is(422))
                .body("title", equalTo("Ops... Parece algo está faltando!"));
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    void testNaoRegistrarVendaQuandoQuantidadeDeProdutosEhMenorQueAQuantidadeDeVendas() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(generateVendaJson(100, 205))
                .when().post()
                .then().statusCode(400);
    }

    String generateVendaJson(Integer quantidade, Integer idProduto) {
        return String.format("""
                {
                    "quantidade": %d,
                    "produto": {
                        "id": %d
                    }
                }
                """, quantidade, idProduto);
    }
}
