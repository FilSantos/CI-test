package automacao.test.testng.api;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import automacao.framework.api.APIContent;
import automacao.framework.api.APIRest;
import automacao.framework.database.H2sql;
import automacao.framework.runner.LogConstants;
import automacao.framework.runner.testng.ListenerTestNG;
import automacao.util.DadosUtils;
import io.restassured.response.Response;


@Listeners(ListenerTestNG.class)
public class TestREST {

	final static Logger LOGGER = Logger.getLogger(TestREST.class);
	private APIRest api;
	@BeforeSuite
	public void setup() throws Exception{
		
		PropertyConfigurator.configure(LogConstants.PROPERTIES);
		H2sql.openConnection();
		api = new APIRest("https://hom-webservice.agoracred.com.br/ApiCredito", "/token", "password", "21455456705", "13563452000123", "ach$208518", APIContent.URLENC);
	}

	@Test(dataProvider = "simulaProposta", dataProviderClass = DadosUtils.class, priority = 1)
	public void simulaProposta(String valorParcela) throws Exception{
		HashMap<String, Object> param = new HashMap<String, Object>();
		
		double vlParcela = Double.parseDouble(valorParcela);
		
		param.put("ValorVista", vlParcela);
		param.put("ValorEntrada", vlParcela - 60);
		param.put("DataPrimeiraParcela", "2019-12-28T11:27:51.2788629-03:00");
		param.put("TabelaComercial", 476);
		param.put("Plano", 4);
		
		Response simulaProposta = api.post(APIContent.JSON, param, "/api/propostas/simulacao", null);

		Reporter.log("RETORNO: <BR>");
		Reporter.log(simulaProposta.getBody().asString());
		if (simulaProposta.getBody().asString().contains("Erro ao simular proposta")) {
			Assert.fail("Erro ao simular proposta");
		}
		
		
		
		
		
		
		//proposta.simulacaoValorCompra("100000", "10000", null, "CDC", "476 - CARNÊ", 0, "Filipe", cpfCliente, "teste automatizado");
	}
	
}
