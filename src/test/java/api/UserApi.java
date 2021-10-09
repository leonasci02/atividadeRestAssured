package api;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.Data;
import org.junit.jupiter.api.*;


import java.util.Locale;

import static io.restassured.RestAssured.given;

public class UserApi {

    private String idUser = null;
    private String nameUser = null;
    private String email = null;
    private String basePath = "/public/v1";
    private RequestSpecification authorization;
    private final String TOKEN = "c07b3d3ff5dca92a9fb69a614169abf9b3319fd6c5ba505832b6d67ff481b0d8";
    private final String MESSAGE_ERROR = "Resource not found";
    private final int STATUS_CODE_OK = 200;
    private final int STATUS_CODE_ERROR = 404;
    private final int STATUS_CODE_CREATED = 201;

    @BeforeEach
    public void setUp(){
        System.out.println("Starting preconditions.");
        RestAssured.baseURI = "https://gorest.co.in";
        authorization = given().header("Authorization", "Bearer " + TOKEN);
        generate();
    }

    @Test
    @DisplayName("Request to validate a user")
    public void userSuccess(){
        System.out.println("Request GET for a new user");
        Response response = authorization.basePath(basePath).get(String.format("/users/%s", this.idUser));
        Assertions.assertEquals(STATUS_CODE_OK, response.getStatusCode());
        Assertions.assertEquals(this.nameUser, response.getBody().jsonPath().getString("data.name"));
        Assertions.assertEquals(this.email, response.getBody().jsonPath().getString("data.email"));
    }

    @Test
    @DisplayName("Request to validate an invalid user")
    public void userFail(){
        System.out.println("Request GET for a invalid user");
        Response response = authorization.basePath(basePath).get(String.format("/users/%s99", this.idUser));
        Assertions.assertEquals(STATUS_CODE_ERROR, response.getStatusCode());
        Assertions.assertEquals(MESSAGE_ERROR, response.getBody().jsonPath().getString("data.message"));
    }

    private void generate(){
        System.out.println("Submitting a new user.");
        Response response =  authorization.basePath(basePath).header("Content-Type", "application/json").body(createdBodyUser()).post("/users");
        Assertions.assertEquals(STATUS_CODE_CREATED, response.getStatusCode());
        this.idUser = String.valueOf(response.getBody().jsonPath().getInt("data.id"));
        System.out.println("New user id: " + this.idUser);
    }

    private String createdBodyUser(){
        Faker faker = new Faker(new Locale("pt-BR"));
        System.out.println("Create a new user.");
        this.nameUser = faker.name().fullName();
        this.email = faker.internet().emailAddress();
        return new Gson().toJson(new Data(this.nameUser, "male", this.email, "active"));
    }
}
