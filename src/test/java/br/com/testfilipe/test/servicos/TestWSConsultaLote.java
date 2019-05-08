package br.com.testfilipe.test.servicos;

import java.io.UnsupportedEncodingException;

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
public class TestWSConsultaLote {
	
	private static String uriuatLogin = "https://uat-login.plv-portoseguro.com.br/";
	private static String oAuth = null;
	private static String uriconsulta = "https://uat-services.plv-portoseguro.com.br/";
	
	static {
		String body = "{\r\n" + 
				"                \"client_id\": \"RUxd5V5O1ceKKvmUGTY4IuRv22O9VeVB\",\r\n" + 
				"                \"client_secret\": \"inUBPhFYKGXnCLNOwqQ9fK_SD6q9TrUXbAMDnosV4S5PIe4P1Y3EG0VP12wdj_Uk\",\r\n" + 
				"                \"grant_type\": \"client_credentials\",\r\n" + 
				"                \"audience\": \"https://uat-services.plv-portoseguro.com.br\"\r\n" + 
				"}";
		
		RestAssured.baseURI =uriuatLogin;
		RequestSpecification given = RestAssured.given();
		given.contentType(ContentType.JSON);
		given.body(body);
		RequestSpecification httpRequest = given;
		Response response = httpRequest.post("/oauth/token");
		Assert.assertEquals("Response", 200, response.getStatusCode());
		
		JsonPath jsonPath = response.body().jsonPath();
		oAuth = "Bearer " + jsonPath.get("access_token");
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
