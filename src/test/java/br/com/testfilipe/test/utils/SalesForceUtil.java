package br.com.testfilipe.test.utils;

import java.util.HashMap;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SalesForceUtil {

	private static String uriuatLogin = "https://test.salesforce.com/services/oauth2/";
	private static String oAuth = null;
	private static String instanceURL = null;
	
	private static HashMap<String, String> oAuthResult = new HashMap<String, String>();
	
	private static void getAuth(){

		if (oAuth== null) {
			HashMap<String, String> param = new HashMap<String, String>();
			 
			 param.put("grant_type", "password"); 
			 param.put("client_id", "3MVG9srSPoex34FW_KPVdBKg5IaEtGHUJ0aii2.YXFfhSROg29UcyJC7MMLMalm.Y..KfmbXfCx_z5M8.xaVm"); 
			 param.put("client_secret", "D362E4E89183EDA44AE7EF796D6119F03D6E804CD3CAF95814BAD79CDD866303"); 
			 param.put("username", "filipe.santos@portoseguro.com.br.uat"); 
			 param.put("password", "235802@GlbXcY0iGYXPdkNsSual0Y5ihg"); 
			
			RestAssured.baseURI =uriuatLogin;
			RequestSpecification given = RestAssured.given();
			given.contentType(ContentType.URLENC);
			given.formParams(param);
			RequestSpecification httpRequest = given;
			Response response = httpRequest.post("/token");
			Assert.assertEquals(200, response.getStatusCode(), "Resposta do oAuth - Sales Force");
			
			JsonPath jsonPath = response.body().jsonPath();
			oAuth = "Bearer " + jsonPath.get("access_token");
			instanceURL = jsonPath.get("instance_url");	
		}
	}
	
	public static String getQuery(String query) {
		getAuth();
		Response response = RestAssured.given()
                .header("Authorization", oAuth)
                .queryParam("q", query)
                .when()
                .get(instanceURL + "/services/data/v45.0/query/")
                .then()
                .extract()
                .response();

		Assert.assertEquals(200, response.getStatusCode(), "Resposta da Query");
		return response.body().asString();
	}
	
	
	
	
}
