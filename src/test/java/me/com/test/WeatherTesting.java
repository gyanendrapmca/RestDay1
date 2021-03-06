package me.com.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class WeatherTesting {

	@Test(description = "Get Weather Response Body")
	public void getWeatherDetails() {
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		System.out.println("Port Number: "+RestAssured.port);
		System.out.println("Base URI: "+RestAssured.baseURI);
		System.out.println("Base Path: "+RestAssured.basePath);

		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET, "/delhi");
		System.out.println("Response Header Size: "+response.getHeaders().size());
		System.out.println("Response Status Line: "+response.getStatusLine());
		String responseBody = response.getBody().asString();
		System.out.println("Response Body: "+responseBody);
		Assert.assertEquals(response.getStatusCode(), 200, "Correct status code returned");
		Assert.assertEquals(response.getStatusLine(), "HTTP/1.1 200 OK", "Correct status Line returned");
	}

	@Test(description = "Verify Content-Type, Server & Content-Encoding")
	public void verifyHeaderType() {
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET, "/chandigarh");

		System.out.println("Content-Type Value: "+response.getHeader("Content-Type"));
		System.out.println("Server Type: "+response.getHeader("Server"));
		System.out.println("Content Encoding: "+response.getHeader("Content-Encoding"));
		Assert.assertEquals(response.getHeader("Content-Type"), "application/json");
		Assert.assertEquals(response.getHeader("Server"), "nginx");
		Assert.assertEquals(response.getHeader("Content-Encoding"), "gzip");
	}

	@Test(description = "Verify Weather Header Section")
	public void verifyWeatherHeader() {
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET, "/chennai");

		Headers allHeaders = response.headers();
		for(Header header: allHeaders)
			System.out.println("Header Key: "+header.getName()+" Header Value: "+header.getValue());
	}

	@Test(description = "Get Response Body")
	public void getResponseBody() {
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET, "/goa");
		
		System.out.println("Body Response");
		System.out.println(response.body().asString());
		System.out.println("GET Body Response");
		System.out.println(response.getBody().asString());
	}
	
	@Test(description = "Verify City in Json Response")
	public void verifyCityJsonResponse() {
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET, "/lucknow");
		
		JsonPath jsonPathValue = response.jsonPath();
		
		System.out.println("City received from response: "+jsonPathValue.get("City"));
		System.out.println("Temperature received from response: "+jsonPathValue.get("Temperature"));
		System.out.println("Humidity received from response: "+jsonPathValue.get("Humidity"));
		System.out.println("WeatherDescription received from response: "+jsonPathValue.get("WeatherDescription"));
		System.out.println("WindSpeed received from response: "+jsonPathValue.get("WindSpeed"));
		System.out.println("Wind Direction Degree received from response: "+jsonPathValue.get("WindDirectionDegree"));

		Assert.assertEquals(jsonPathValue.get("City"), "Lucknow", "Correct city name received in the response");
	}

}