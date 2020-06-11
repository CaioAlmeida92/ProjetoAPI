package com.githubCaioAlmeida92;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.math.MathContext;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.internal.ResponseSpecificationImpl.HamcrestAssertionClosure;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class UserJsonTest {
	
	@Test
	public void devoValidarPrimeiroNivel() {
		given()
		
		.when()
			.get("http://restapi.wcaquino.me/users/1")
		.then()
			.statusCode(200)
			.body("id", is(1))
			.body("name", containsString("Silva"))
			.body("age", greaterThan(18))
		;	
	}
	
	@Test
	public void deveVerificarPrimeiroNivelDeOutrasFormas() {
		Response response = request(Method.GET, "http://restapi.wcaquino.me/users/1");
		
		//path
		Assert.assertEquals(1, response.path("id"));
		
		//json path
		JsonPath jpath = new JsonPath(response.asString());
		Assert.assertEquals(1, jpath.getInt("id"));
			
	}
	
	@Test
	public void devoVerificarSegundoNivel() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/users/2")
		.then()
			.statusCode(200)
			.body("endereco.rua", containsString("Rua"))
			
		;
	}
	
	@Test
	public void devoValidarLista() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/users/3")
		.then()
			.statusCode(200)
			.body("name", containsString("Ana"))
			.body("filhos", hasSize(2))
			.body("filhos[0].name", is("Zezinho"))
			.body("filhos[1].name", is("Luizinho"))
			.body("filhos.name", hasItem("Zezinho"))
		;	
	}
	
	@Test
	public void deveRetornarUsuarioInexistente() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/users/4")
		.then()
			.statusCode(404)
			.body("error", is("Usuário inexistente"))
			//Teste
		;	
	}
}
