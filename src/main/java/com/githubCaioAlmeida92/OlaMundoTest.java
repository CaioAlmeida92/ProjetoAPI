package com.githubCaioAlmeida92;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundoTest {
	
	@Test
	public void testOlaMundo() {
		Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
		Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
		Assert.assertTrue("O status code deveria ser 201", response.statusCode() == 200);
		Assert.assertEquals(200, response.statusCode());
		
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
	}
	
	@Test
	public void devoConhecerOutrasFormasRestAssured() {
		Response response = request(Method.GET, "http://restapi.wcaquino.me/ola");
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
		
		get("http://restapi.wcaquino.me/ola").then().statusCode(200);
		
		given()
		
		.when()
			.get("http://restapi.wcaquino.me/ola")
		.then()
			.assertThat()
			.statusCode(200)
		;
	}
	
	@Test
	public void devoConhecerOsMatchersComHamcrest() {
		Assert.assertThat("Maria", Matchers.is("Maria"));
		Assert.assertThat(120, Matchers.isA(Integer.class));
		assertThat(120, greaterThan(119));
		
		List<Integer> impares = Arrays.asList(1, 3, 5, 7);
		assertThat(impares, hasSize(4));
		assertThat(impares, containsInAnyOrder(1, 3, 5, 7));
		assertThat(impares, hasItem(3));
		
		assertThat("Maria", anyOf(is("Maria"), is("Joaquina")));
		assertThat("Joaquina", allOf(startsWith("Joa"), endsWith("ina"), containsString("aqu")));
	}
	
	@Test
	public void devoValidarOBody() {
		given()
		
		.when()
			.get("http://restapi.wcaquino.me/ola")
		.then()
			.statusCode(200)
			.body(is("Ola Mundo!"))
			.body(containsString("Mun"))
			.body(is(not(nullValue())))
		;
	}
}
