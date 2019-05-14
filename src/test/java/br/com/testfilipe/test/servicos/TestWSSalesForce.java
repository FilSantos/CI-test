package br.com.testfilipe.test.servicos;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestWSSalesForce {
	
	private static String uriuatLogin = "https://test.salesforce.com/services/oauth2/";
	private static String oAuth = null;
	private static String instanceURL = null;
	private static String uriconsulta = "https://uat-services.plv-portoseguro.com.br/";
	
	static {
		
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
		Assert.assertEquals("Response", 200, response.getStatusCode());
		
		JsonPath jsonPath = response.body().jsonPath();
		oAuth = "Bearer " + jsonPath.get("access_token");
		instanceURL = jsonPath.get("instance_url");
	}	
	
	
	@Test
	public void getLote()
	{
		
		String body = "{\r\n" + 
				"    \"id\" : \"\",\r\n" + 
				"    \"codigoparceiro\" : \"\",\r\n" + 
				"    \"dadosbusca\" : [\r\n" + 
				"         {   \r\n" + 
				"            \"tabela\" : \"estoque\",\r\n" + 
				"            \"criterios\" : [\r\n" + 
				"                {\r\n" + 
				"                    \"campo\" : \"idlote\",\r\n" + 
				"                    \"operador\" : \"=\",\r\n" + 
				"                    \"valor\" : \"16\"\r\n" + 
				"                }\r\n" + 
				"            ]\r\n" + 
				"        }\r\n" + 
				"    ] \r\n" + 
				"}";
		RestAssured.baseURI =uriconsulta;
		RequestSpecification given = RestAssured.given();
		given.contentType(ContentType.JSON);
		given.body(body);
		given.header("Authorization", oAuth);
		
		RequestSpecification httpRequest = given;
		Response response = httpRequest.post("api/arquivos/consulta");
		Assert.assertEquals("Response", 200, response.getStatusCode());
		
		JsonPath jsonPath = response.body().jsonPath();
		Assert.assertEquals("Response", "OK", jsonPath.get("meta.mensagem"));
		//String decoded = new String(DatatypeConverter.parseBase64Binary(jsonPath.get("data")));
		try {
			String decoded = new String(jsonPath.get("data"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
}
