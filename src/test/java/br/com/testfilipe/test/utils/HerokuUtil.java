package br.com.testfilipe.test.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.xml.bind.DatatypeConverter;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class HerokuUtil {
	
	private static String uriuatLogin = "https://login-uat.vidacloud-portoseguro.com.br/oauth";
	private static String oAuth = null;
	private static String uriconsulta = "https://api-uat.vidacloud-portoseguro.com.br/api";
	
	static {
		
		 HashMap<String, String> param = new HashMap<String, String>();
		 param.put("audience", "https://api-uat.vidacloud-portoseguro.com.br");
		 param.put("grant_type", "client_credentials");
		 param.put("client_secret", "inUBPhFYKGXnCLNOwqQ9fK_SD6q9TrUXbAMDnosV4S5PIe4P1Y3EG0VP12wdj_Uk");
		 param.put("client_id", "RUxd5V5O1ceKKvmUGTY4IuRv22O9VeVB"); 
	
		RestAssured.baseURI =uriuatLogin;
		RequestSpecification given = RestAssured.given();
		given.contentType(ContentType.JSON);
		given.body(param);
		RequestSpecification httpRequest = given;
		Response response = httpRequest.post("/token");

		if (response.getStatusCode() == 200){
			
			JsonPath jsonPath = response.body().jsonPath();
			oAuth = "Bearer " + jsonPath.get("access_token");
		}
		

	}	
	
	public static String getLote(){
		
		String path = null;
		
		HashMap<String, String> criterio = new HashMap<String, String>();
		criterio.put("campo", "idlote");
		criterio.put("operador", "=");
		criterio.put("valor", "2909");
		
		List<HashMap<String, String>> listCriterio = new ArrayList<HashMap<String, String>>();
		listCriterio.add(criterio);
		
		HashMap<String, Object> dadosBusca = new HashMap<String, Object>();
		dadosBusca.put("tabela", "estoque");
		dadosBusca.put("criterios", listCriterio);
		
		List<HashMap<String, Object>> listDadosBusca = new ArrayList<HashMap<String, Object>>();
		listDadosBusca.add(dadosBusca);
		
		HashMap<String, Object> lote = new HashMap<String, Object>();
		lote.put("id", "");
		lote.put("codigoparceiro", "");
		lote.put("dadosbusca", listDadosBusca); 

		RestAssured.baseURI =uriconsulta;
		RequestSpecification given = RestAssured.given();
		given.contentType(ContentType.JSON);
		given.header("Authorization", oAuth);
		given.body(lote);
		RequestSpecification httpRequest = given;
		Response response = httpRequest.post("/arquivos/consulta");
		if (response.getStatusCode() == 200){
			JsonPath jsonPath = response.body().jsonPath();
			if(((String)jsonPath.get("meta.mensagem")).equals("OK")){
				byte[] data = DatatypeConverter.parseBase64Binary(jsonPath.get("data"));
				
				
				try {
					path = new java.io.File( "." ).getCanonicalPath() + "/prodata/loteHeroku.tar.gz";
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    File file = new File(path);
			    try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
		            outputStream.write(data);
		            outputStream.close();
		            
		            File archive = new File(path);
		            File destination = new File(new java.io.File( "." ).getCanonicalPath() + "/prodata/lote/");

		            byte[] buffer = new byte[1024];

		           	 GZIPInputStream gzis = new GZIPInputStream(new FileInputStream(archive));
		        
		           	 FileOutputStream out = new FileOutputStream(destination);
		        
		               int len;
		               while ((len = gzis.read(buffer)) > 0) {
		               	out.write(buffer, 0, len);
		               }
		        
		               gzis.close();
		           	out.close();
		           	path = new java.io.File( "." ).getCanonicalPath() + "/prodata/lote";
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			}
		}
		
		return path;
	}
	
	public static String getParcelas(String iDContract, String iDContratoContratante){
		
		RestAssured.baseURI =uriconsulta;
		RequestSpecification given = RestAssured.given();
		given.contentType(ContentType.JSON);
		given.header("Authorization", oAuth);
		
		RequestSpecification httpRequest = given;
		Response response = httpRequest.get(String.format("/parcelascontrato?%s_%s", iDContract, iDContratoContratante));
		return response.body().asString();
	}
	
}
