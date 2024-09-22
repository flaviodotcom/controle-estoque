package com.github.estoque.resource;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import io.quarkus.security.Authenticated;

import java.util.List;

@Path("/admin")
@Authenticated
public class RolesResource {

    @Inject
    Keycloak keycloak;

    @PostConstruct
    public void initKeycloak() {
        keycloak = KeycloakBuilder.builder()
                .serverUrl("http://localhost:8180")
                .realm("master")
                .clientId("admin-cli")
                .grantType("password")
                .username("admin")
                .password("admin")
                .build();
    }

    @PreDestroy
    public void closeKeycloak() {
        keycloak.close();
    }

    @GET
    @Path("/roles")
    @RolesAllowed("admin")
    public List<RoleRepresentation> getRoles() {
        return keycloak.realm("quarkus").roles().list();
    }

    @GET
    @Path("/users")
    @RolesAllowed("admin")
    public List<UserRepresentation> getUsers() {
        return keycloak.realm("quarkus").users().list();
    }

}