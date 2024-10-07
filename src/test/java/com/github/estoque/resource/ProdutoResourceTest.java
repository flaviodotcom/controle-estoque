package com.github.estoque.resource;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

@QuarkusTest
@TestHTTPEndpoint(ProdutoResource.class)
class ProdutoResourceTest {

    @BeforeAll
    static void setup() {
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    void testGetProdutoById() {
        given()
                .when().get("/203")
                .then()
                .statusCode(200)
                .body("id", is(203))
                .body("nome", is("Pão"))
                .body("preco", is(1.00f))
                .body("quantidade", is(1))
                .body("dataValidade", notNullValue());
    }

    @Test
    @TestSecurity(user = "jose", roles = {"admin", "user"})
    void testGetAllProdutos() {
        given()
                .when().get()
                .then()
                .statusCode(200)
                .body("size() > 0", is(true));
    }

    @Test
    @TestSecurity(user = "jose", roles = {"admin", "user"})
    void testCadastrarProdutoComAutorizacao() {
        given()
                .contentType(ContentType.JSON)
                .body(generateProdutoJson("Coxinha", new BigDecimal("5.00"), 2))
                .when().post()
                .then().statusCode(201);
    }

    @Test
    @TestSecurity(user = "alice", roles = "user")
    void testCadastrarProdutoSemAutorizacao() {
        given()
                .contentType(ContentType.JSON)
                .body(generateProdutoJson("Copo de café", new BigDecimal("1.00"), 1))
                .when().post()
                .then().statusCode(403);
    }

    @Test
    @TestSecurity(user = "jose", roles = {"admin", "user"})
    void testAtualizarProdutoComAutorizacao() {
        given()
                .contentType(ContentType.JSON)
                .body(generateProdutoJson("Biscoito", new BigDecimal("3.50"), 1))
                .when().put("/204")
                .then().statusCode(204);
    }

    @Test
    @TestSecurity(user = "alice", roles = "user")
    void testAtualizarProdutoSemAutorizacao() {
        given()
                .contentType(ContentType.JSON)
                .body(generateProdutoJson("Refrigerante", new BigDecimal("10.00"), 1))
                .when().put("/204")
                .then().statusCode(403);
    }

    @Test
    @TestSecurity(user = "alice", roles = "user")
    void testProdutosQueEstaoPertoDoVencimento() {
        given()
                .when().get("/vencimento")
                .then().statusCode(200)
                .body("nome.toString().contains(\"Pão\")", is(true));
    }

    @Test
    @TestSecurity(user = "alice", roles = "user")
    void testProdutosQueEstaoVencidos() {
        given()
                .when().get("/vencidos")
                .then().statusCode(200)
                .body("nome", is(List.of("Pão de Queijo")));
    }

    String generateProdutoJson(String nome, BigDecimal preco, Integer quantidade) {
        return String.format("""
                {
                    "nome": "%s",
                    "preco": %s,
                    "quantidade": %d
                }
                """, nome, preco, quantidade);
    }

}
