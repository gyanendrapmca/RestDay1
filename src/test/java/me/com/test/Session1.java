package me.com.test;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.json.simple.JSONObject;

public class Session1 {
	
	@Test(description="Use Static Import")
	public void test1() {
		given().
			get("https://reqres.in/api/users?page=2").
		then().
			statusCode(200).
			body("data.id[1]",equalTo(8)).
			body("data.first_name", hasItems("Michael", "Lindsay"));
	}

	@SuppressWarnings("unchecked")
	@Test(description = "Post Method")
	public void test2() {
		JSONObject parameters = new JSONObject();
		parameters.put("name", "Ravi");
		parameters.put("job", "QA/Engineer");
		
		given().
			header("Content-Type", "application/json").
			contentType(ContentType.JSON).
			accept(ContentType.JSON).
			body(parameters.toJSONString()).
		when().
			post("https://reqres.in/api/users").
		then().
			statusCode(201);
	}
}
