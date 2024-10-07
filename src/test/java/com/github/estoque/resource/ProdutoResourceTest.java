package com.github.estoque.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

@QuarkusTest
class ProdutoResourceTest {

    private final String jsonBody = """
            {
                "nome": "Refrigerante",
                "preco": 8.0,
                "quantidade": 1.0
            }""";

    @BeforeAll
    static void setup() {
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    void testGetProdutoById() {
        given()
                .when().get("/estoque/203")
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
                .when().get("/estoque")
                .then()
                .statusCode(200)
                .body("size() > 0", is(true));
    }

    @Test
    @TestSecurity(user = "jose", roles = {"admin", "user"})
    void testCadastrarProdutoComAutorizacao() {
        given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when().post("/estoque")
                .then().statusCode(201);
    }

    @Test
    @TestSecurity(user = "alice", roles = "user")
    void testCadastrarProdutoSemAutorizacao() {
        given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when().post("/estoque")
                .then().statusCode(403);
    }

    @Test
    @TestSecurity(user = "jose", roles = {"admin", "user"})
    void testAtualizarProdutoComAutorizacao() {
        given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when().put("/estoque/204")
                .then().statusCode(204);
    }

    @Test
    @TestSecurity(user = "alice", roles = "user")
    void testAtualizarProdutoSemAutorizacao() {
        given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when().put("/estoque/204")
                .then().statusCode(403);
    }

    @Test
    @TestSecurity(user = "alice", roles = "user")
    void testProdutosQueEstaoPertoDoVencimento() {
        given()
                .when().get("/estoque/vencimento")
                .then().statusCode(200)
                .body("nome.toString().contains(\"Pão\")", is(true));
    }

    @Test
    @TestSecurity(user = "alice", roles = "user")
    void testProdutosQueEstaoVencidos() {
        given()
                .when().get("/estoque/vencidos")
                .then().statusCode(200)
                .body("nome", is(List.of("Pão de Queijo")));
    }

}
