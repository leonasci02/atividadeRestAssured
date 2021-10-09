package api;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.User;
import org.junit.jupiter.api.*;


import java.util.Locale;

import static io.restassured.RestAssured.given;

public class UserApi {

    private String idUsuario = null;
    private String nomeUsuario = null;
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
        RestAssured.baseURI = "https://gorest.co.in";
        authorization = given().header("Authorization", "Bearer " + TOKEN);
        geraMassa();
    }

    @Test
    @DisplayName("Requisição para validar um usuário")
    public void userSuccess(){
        Response response = authorization.basePath(basePath).get(String.format("/users/%s", this.idUsuario));
        Assertions.assertEquals(STATUS_CODE_OK, response.getStatusCode());
        Assertions.assertEquals(this.nomeUsuario, response.getBody().jsonPath().getString("data.name"));
        Assertions.assertEquals(this.email, response.getBody().jsonPath().getString("data.email"));
    }

    @Test
    @DisplayName("Requisição para validar um usuário inexistente")
    public void userFail(){
        Response response = authorization.basePath(basePath).get("/users/1957");
        Assertions.assertEquals(STATUS_CODE_ERROR, response.getStatusCode());
        Assertions.assertEquals(MESSAGE_ERROR, response.getBody().jsonPath().getString("data.message"));
    }

    public void geraMassa(){
       Response resposta =  authorization.basePath(basePath).header("Content-Type", "application/json").body(bodyUsuario()).post("/users");
       Assertions.assertEquals(STATUS_CODE_CREATED, resposta.getStatusCode());
       this.idUsuario = String.valueOf(resposta.getBody().jsonPath().getInt("data.id"));
    }

    public void geraMassaObject(){
        Response resposta =  authorization.basePath(basePath).header("Content-Type", "application/json").body(bodyUsuario()).post("/users");
        Assertions.assertEquals(STATUS_CODE_CREATED, resposta.getStatusCode());
        this.idUsuario = String.valueOf(resposta.getBody().jsonPath().getInt("data.id"));
    }

    public String bodyUsuario(){
        Faker faker = new Faker(new Locale("pt-BR"));
        this.nomeUsuario = faker.name().fullName();
        this.email = faker.internet().emailAddress();
        return String.format("{\"name\":\"%s\", \"gender\":\"male\", \"email\":\"%s\", \"status\":\"active\"}", this.nomeUsuario, email);
    }
}
