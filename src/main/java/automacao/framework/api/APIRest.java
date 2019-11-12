package automacao.framework.api;

import java.util.HashMap;

import org.apache.log4j.Logger;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIRest {

	private String uri;
	private String oAuth;
	private JsonPath jsonPath;
	private HashMap<String, String> param;
	final static Logger logger = Logger.getLogger(APIRest.class);
	
	private void insereParam(String key, String value){
		if (value !=null){
			param.put(key, value);
		}
	}

	public APIRest (String uri, String path, String grantType, String clientId, String clienteSecret, String username, String password, APIContent contentType) throws Exception{
		
		this.uri = uri;
		
		param = new HashMap<String, String>();
		insereParam("grant_type",grantType);
		insereParam("client_id",clientId);
		insereParam("client_secret",clienteSecret);
		insereParam("username",username);
		insereParam("password",password);

		logger.info(String.format("Autenticando no  %s", this.uri));

		RequestSpecification given = RestAssured.given().baseUri(this.uri);
		given.contentType(contentType.getContent());
		given.formParams(param);
		RequestSpecification httpRequest = given;
		Response response = httpRequest.post(path);
		if (response.getStatusCode() == 200) {
			jsonPath = response.body().jsonPath();
			this.oAuth = "Bearer " + jsonPath.get("access_token");
		} else {
			throw new Exception("Erro ao autenticar" + response.body().asString());
		}
		
	}

	public Response query(String query, String path, String uriTemp) {
		
		RequestSpecification httpRequest = createHeader(uriTemp);
		Response response = httpRequest.queryParam("q", query).get(path).then().extract().response();
		return response;
	}
	
	public Response get(String path, String uriTemp) {
		
		RequestSpecification httpRequest = createHeader(uriTemp);
		Response response = httpRequest.get(path).then().extract().response();
		return response;
	}

	public Response get(String request, String path, String uriTemp) {
		
		RequestSpecification httpRequest = createHeader(uriTemp);
		Response response = httpRequest.body(request).get(path).then().extract().response();
		return response;
	}
	
	public Response post(APIContent contentType, HashMap<Object, Object> param, String path, String uriTemp) {
		
		RequestSpecification httpRequest = createHeader(uriTemp);
		Response response = httpRequest.body(param).contentType(contentType.getContent()).post(path);
		return response;
	}
	
	public Response patch(APIContent contentType, HashMap<Object, Object> param, String path, String uriTemp) {
		
		RequestSpecification httpRequest = createHeader(uriTemp);
		Response response = httpRequest.body(param).contentType(contentType.getContent()).patch(path);
		return response;
	}
	
	public Response delete(APIContent contentType, HashMap<Object, Object> param, String path, String uriTemp) {
		
		RequestSpecification httpRequest = createHeader(uriTemp);
		Response response = httpRequest.body(param).contentType(contentType.getContent()).delete(path);
		return response;
	}

	private RequestSpecification createHeader(String uriTemp) {

		RequestSpecification given = RestAssured.given().baseUri(uriTemp != null ? uriTemp : uri);
		given.header("Authorization", oAuth);
		RequestSpecification httpRequest = given;
		return httpRequest;
	}
}