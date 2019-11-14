package automacao.framework.runner.testng;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

import automacao.framework.browser.command.CommandAction;
import automacao.framework.browser.command.CommandType;
import automacao.framework.files.FilesAction;
import automacao.framework.runner.LogConstants;

public class ListenerTestNG implements ITestListener, ISuiteListener,IReporter {

	final static Logger logger = Logger.getLogger(ListenerTestNG.class);
	private String suiteName = "AUTOMACAO";	
	private static List<WebDriver> webDrivers = new ArrayList<WebDriver>();
	
	List<ITestResult> passedtests = new ArrayList<ITestResult>();
	List<ITestResult> failedtests = new ArrayList<ITestResult>();
	List<ITestResult> skippedtests = new ArrayList<ITestResult>();

	static {
		PropertyConfigurator.configure(LogConstants.PROPERTIES);
		if (modoDev()) {
			System.out.println("Modo desenvovedor");	
		}
		
	}

	public static boolean modoDev() {
		return System.getProperty("java.library.path").toLowerCase().contains("eclipse");
	}

	public static void setWebdriver(WebDriver running) {
		webDrivers.add(running);
	}

	@Override
	public void onStart(ISuite suite) {
		
		Thread.currentThread().setName(suiteName);
	}

	@Override
	public void onFinish(ISuite suite) {

		System.out.println("Fim de execução - Resumo");
		System.out.println("Testes finalizados com exito: " + passedtests.size());
		System.out.println("Testes finalizados com falha: " + failedtests.size());
		System.out.println("Testes finalizados não executados: " + skippedtests.size() + "\n");
		try {
			for (ITestResult resultTest : failedtests) {
				System.out.println(String.format("Teste %s - Motivo: %s", resultTest.getMethod().getMethodName().toString(),
						resultTest.getThrowable().getMessage().toString()));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
				
		try {
			for (ITestResult resultTest : skippedtests) {
				System.out.println(String.format("Teste %s - Motivo: %s", resultTest.getMethod().getMethodName().toString(),
						resultTest.getThrowable().getMessage().toString()));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	public void onTestStart(ITestResult result) {
		Thread.currentThread().setName(result.getName());
		logger.info("Iniciando teste...");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		passedtests.add(result);
		finalizaTestMensagem(result);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		failedtests.add(result);

		finalizaTestMensagem(result);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		skippedtests.add(result);
		finalizaTestMensagem(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		failedtests.add(result);
		finalizaTestMensagem(result);
	}

	@Override
	public void onStart(ITestContext context) {
		if (!suiteName.equals(Thread.currentThread().getName())) {
			Thread.currentThread().setName(suiteName);
		}
	}

	@Override
	public void onFinish(ITestContext context) {
		Thread.currentThread().setName(suiteName);
	}

	private void finalizaTestMensagem(ITestResult result)  {
		Object[] parameters = result.getParameters();
		
		Throwable exception = result.getThrowable();
		if(exception!=null)			
		{
			if(exception.getMessage().startsWith("** WARNING:")){
				result.setStatus(ITestResult.SUCCESS_PERCENTAGE_FAILURE);
			}
		}

		String msg = "Teste " + result.getName()
				+ (parameters.length != 0 ? " com os dados " + Arrays.toString(parameters) + ", " : " ")
				+ "finalizou com situação "
				+ (result.getStatus() == ITestResult.SUCCESS ? "sucesso"
						: result.getStatus() == ITestResult.FAILURE ? "falha"
								: result.getStatus() == ITestResult.SUCCESS_PERCENTAGE_FAILURE
										? "falha, porém necessário avaliação para avaliar possivel sucesso"
										: "ignorado");

		if (result.getStatus() != ITestResult.SUCCESS) {
			if (webDrivers.size() != 0) {
				for (WebDriver running : webDrivers) {
					CommandAction command = CommandType.WEB.getCommand(running);
					try {
						command.screenshot();
					} catch (Exception e) {
						logger.error("Erro ao capturar o screenshot!", e);
					}
				}
			}
		}

		for (WebDriver running : webDrivers) {
			if ((running instanceof ChromeDriver) | (running instanceof FirefoxDriver)) {
				if (result.getStatus() != ITestResult.SKIP) {
					try {
						Cookie cookie = new Cookie("zaleniumTestPassed",
								result.getStatus() == ITestResult.SUCCESS ? "true" : "false");
						running.manage().addCookie(cookie);
					} catch (Exception e) {
						logger.error("Não é possível definir o cookie de resultado do teste", e);
					}
				}
			}
		}

		logger.info(msg);
		System.out.println();
		Thread.currentThread().setName(suiteName);
	}

	/** Anexo o arquivo
	 * @author Filipe Santos(cognizant)
	 * @param source
	 * @throws Exception
	 */
	public static void attachFile(File source) throws Exception {
		String fileName = source.getName();
		String folder = new java.io.File(".").getCanonicalPath() + "/relatorio/arquivos/";
		FileUtils.copyFile(source, new File(folder + fileName));
		String path = ("Arquivo: <a href=./arquivos/" + fileName + ">" + fileName + "</a>");
		Reporter.log("<br>");
		Reporter.log(path);
		Reporter.log("<br>");
	}
	
	/** Gera ao final da execução, o relatório de testes
	 * @author Filipe Santos
	 */
	@Override
	public void generateReport(List<XmlSuite> xmlSuite, List<ISuite> suites, String outputDirectory) {

		try{
			logger.info("Gerando o relatorio...");
			// Carrega o nome do Relatorio
			String customReportTemplateStr = readEmailabelReportTemplate();
			
			// Gera o nome do relatorio
			String customReportTitle = getCustomReportTitle("Relatorio de Execucao de testes",false);
			
			// Carrega as suites
			String customSuiteSummary = this.getTestSuiteSummary(suites);
			
			// Carrega os testes
			String customTestMethodSummary = this.getTestMehodSummary(suites);
			
			// Grava o titulo
			customReportTemplateStr = customReportTemplateStr.replaceAll("\\$TestNG_Custom_Report_Title\\$", customReportTitle);
			
			// grava o resumo da execução
			customReportTemplateStr = customReportTemplateStr.replaceAll("\\$Test_Case_Summary\\$", customSuiteSummary);
			
			// grava o detalhamento
			customReportTemplateStr = customReportTemplateStr.replace("Test_Case_Detail", customTestMethodSummary);
			
			// Grava o arquivo
			File targetFile = new File(new java.io.File( "." ).getCanonicalPath() + getCustomReportTitle("/relatorio/execucao",true) + ".html");
			FileWriter fw = new FileWriter(targetFile);
			fw.write(customReportTemplateStr);
			fw.flush();
			fw.close();
			logger.info("Relatorio gerado com sucesso!");
		}catch(Exception e)
		{
			logger.error("Erro ao gerar o relatorio",e);
		}
	}
	
	/** Carrega o template do relatorio
	 * @author Filipe Santos
	 * @return
	 */

	private String readEmailabelReportTemplate() {
		StringBuffer retBuf = new StringBuffer();
		
		try {
			
			FilesAction fileAction = new FilesAction();
			if (!fileAction.extractFromMainResources("relatorio/logo.png")){
				logger.error("Erro ao extrair o logo do relatorio.");
			}
		
			File file = new File(fileAction.getFromMainResources("/relatorio/relatorio-template.html").toURI());
			FileReader fr = new FileReader(file);

			BufferedReader br = new BufferedReader(fr);
			
			String line = br.readLine();
			while(line!=null)
			{
				retBuf.append(line);
				line = br.readLine();
			}
			br.close();
			return retBuf.toString();
		} catch (Exception ex) {
			logger.error(ex);
			return null;
		}
		
	}
	/**Converte no formato dia/hora
	 * @author Filipe Santos
	 * @param date
	 * @return
	 */
	private String getDateInStringFormat(Date date){
		
		StringBuffer retBuf = new StringBuffer();
		
		if(date==null)
		{
			date = new Date();
		}
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		retBuf.append(df.format(date));
		return retBuf.toString();
	}
	
	/** Converte numero para hora
	 * @author Filipe Santos
	 * @param deltaTime
	 * @return
	 */
	private String convertDeltaTimeToString(long deltaTime) {
		StringBuffer retBuf = new StringBuffer();
		
		@SuppressWarnings("unused")
		long milli = deltaTime;
		long seconds = deltaTime / 1000;		
		long minutes = seconds / 60;		
		long hours = minutes / 60;		
		retBuf.append(hours + ":" + minutes + ":" + seconds);
		return retBuf.toString();
	}
	
	/** Acrescenta o time stamp ao Relatório de acordo com a sua utilização
	 * @author Filipe Santos
	 * @param title
	 * @param reportFileName
	 * @return
	 */
	private String getCustomReportTitle(String title, boolean reportFileName){
		StringBuffer retBuf = new StringBuffer();
		
		retBuf.append(title);
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(reportFileName? "yyyyMMddHHmmss": " dd/MM/yyyy HH:mm:ss");
		retBuf.append(df.format(date));
			
		return retBuf.toString();
	}
	
	/** Recupera todos os resultados dos testes por Classe e sumariza os dados gerais
	 * @author Filipe Santos
	 * @param suites
	 * @return
	 */
	@SuppressWarnings("finally")
	private String getTestSuiteSummary(List<ISuite> suites){
		
		StringBuffer retBuf = new StringBuffer();
		
		try{
			int totalTestCount = 0;
			int totalTestPassed = 0;
			int totalTestFailed = 0;
			int totalTestWarning = 0;
			int totalTestSkipped = 0;
			
			for(ISuite tempSuite: suites)
			{
				retBuf.append("<tr><td colspan=11><center><b>Informacao de teste</b></center></td></tr>");
				
				Map<String, ISuiteResult> testResults = tempSuite.getResults();

				boolean first = true;
				Date startDate = null;
				Date endDate = null;
				
				for (ISuiteResult result : testResults.values()) {
					
					ITestContext testObj = result.getTestContext();
					
					totalTestPassed = totalTestPassed + testObj.getPassedTests().getAllMethods().size();
					totalTestSkipped = totalTestSkipped+ testObj.getSkippedTests().getAllMethods().size();
					totalTestWarning = totalTestWarning + testObj.getFailedButWithinSuccessPercentageTests().getAllMethods().size();
					totalTestFailed = totalTestFailed + testObj.getFailedTests().getAllMethods().size();
					totalTestCount = totalTestPassed + totalTestSkipped + totalTestFailed+totalTestWarning;
					
					if(first){
						startDate = testObj.getStartDate();
						first = false;
					}
					
					endDate = testObj.getEndDate();
				}
				retBuf.append("<tr>");
				
				retBuf.append("<td>");
				retBuf.append(totalTestCount);
				retBuf.append("</td>");
				
				retBuf.append("<td>");
				retBuf.append(totalTestPassed);
				retBuf.append("</td>");
				
				retBuf.append("<td bgcolor=#3399ff>");
				retBuf.append(totalTestWarning);
				retBuf.append("</td>");

				retBuf.append("<td bgcolor=#ff6666>");
				retBuf.append(totalTestFailed);
				retBuf.append("</td>");
				
				retBuf.append("<td bgcolor=#ffff99>");
				retBuf.append(totalTestSkipped);
				retBuf.append("</td>");

				retBuf.append("<td>");
				retBuf.append(getDateInStringFormat(startDate));
				retBuf.append("</td>");
				
				retBuf.append("<td>");
				retBuf.append(getDateInStringFormat(endDate));
				retBuf.append("</td>");
				
				long deltaTime = endDate.getTime() - startDate.getTime();
				String deltaTimeStr = convertDeltaTimeToString(deltaTime);
				retBuf.append("<td>");
				retBuf.append(deltaTimeStr);
				retBuf.append("</td>");
				
				retBuf.append("</tr>");
				
			}
		}catch(Exception ex) {
			logger.error(ex);
		}finally {
			
			return retBuf.toString();
		}
	}


	/**Recupera os dados a nivel de suite por tipos de resultado
	 * @author Filipe Santos
	 * @param suites
	 * @return
	 */
	@SuppressWarnings("finally")
	private String getTestMehodSummary(List<ISuite> suites){
		
		StringBuffer retBuf = new StringBuffer();
		List<IResultMap> failed = new ArrayList<IResultMap>();
		List<IResultMap> warning= new ArrayList<IResultMap>();
		List<IResultMap> skipped= new ArrayList<IResultMap>();
		List<IResultMap> passed= new ArrayList<IResultMap>();
		try{
			for(ISuite tempSuite: suites){	
				
				Map<String, ISuiteResult> testResults = tempSuite.getResults();
				for (ISuiteResult result : testResults.values()) {

					//Carrega os testes por status e por suite
					ITestContext testObj = result.getTestContext();
					failed.add(testObj.getFailedTests()) ;
					warning.add(testObj.getFailedButWithinSuccessPercentageTests());
					skipped.add(testObj.getSkippedTests());
					passed.add(testObj.getPassedTests());
				}
			}
			
			for (IResultMap iResultMap : failed) {
				String results = this.getTestMethodReport("Falha", iResultMap);
				retBuf.append(results);

			}
			for (IResultMap iResultMap : warning) {
				String results = this.getTestMethodReport("Aviso", iResultMap);
				retBuf.append(results);
			}
			for (IResultMap iResultMap : skipped) {
				String results = this.getTestMethodReport("Ignorado", iResultMap);
				retBuf.append(results);
			}
			for (IResultMap iResultMap : passed) {
				String results = this.getTestMethodReport("Sucesso", iResultMap);
				retBuf.append(results);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			return retBuf.toString();
		}
	}


	/** Gera o documento na seção de dados dos testes
	 * @author Filipe Santos
	 * @param type
	 * @param testResultMap
	 * @return
	 */
	private String getTestMethodReport(String type, IResultMap testResultMap)
	{
		StringBuffer retStrBuf = new StringBuffer();
		
		String color = "white";
		switch (type) {
		case "Sucesso":
			color = "white";
			break;
		case "Falha":
			color = "#ff6666";
			break;
		case "Ignorado":
			color ="#ffff99";
			break;
		case "Aviso":
			color ="#3399ff";
			break;
		}
		
		if (testResultMap.size() == 0){
			return "";
		}
		
		retStrBuf.append("<tr bgcolor=" + color + "><td colspan=7><center><b>" + type + "</b></center></td></tr>");
			
		Set<ITestResult> testResultSet = testResultMap.getAllResults();
			
		for(ITestResult testResult : testResultSet)
		{
			String testClassName = "";
			String testMethodName = "";
			String startDateStr = "";
			String executeTimeStr = "";
			String paramStr = "";
			String reporterMessage = "";
			String exceptionMessage = "";
			String testID = "";
			
			//carrega suite
			testClassName = testResult.getTestClass().getName();
			testClassName = testClassName.split("\\.")[testClassName.split("\\.").length-1].replace("Test", "");
			
			
			testID = "Output-" + testResult.hashCode();
			//carrega Teste
			testMethodName = testResult.getMethod().getMethodName();
				
			//Carrega hora inicio
			long startTimeMillis = testResult.getStartMillis();
			startDateStr = this.getDateInStringFormat(new Date(startTimeMillis));
				
			//Carrega tempo de execucao
			long deltaMillis = testResult.getEndMillis() - testResult.getStartMillis();
			executeTimeStr = this.convertDeltaTimeToString(deltaMillis);
				
			//Carrega os parametro
			Object paramObjArr[] = testResult.getParameters();
			for(Object paramObj : paramObjArr)
			{
				paramStr += String.valueOf(paramObj) ;
				paramStr += "; ";
			}
				
			//Carrega o detalhe da execucao
			List<String> repoterMessageList = Reporter.getOutput(testResult);
			for(String tmpMsg : repoterMessageList)				
			{
				int tamanhoLinha = 70;
				for (int i = 0; i < tmpMsg.length(); i += tamanhoLinha) {
					
					reporterMessage += tmpMsg.substring(i, Math.min(i + tamanhoLinha, tmpMsg.length())) + "<br>";
					}
			}
				
			//Carrega o motivo de erro ou aviso
			
			//arruma
			Throwable exception = testResult.getThrowable();
			if(exception!=null)			
			{
				exceptionMessage = exception.getMessage().replace("** WARNING:", "");
			}
			exceptionMessage = exceptionMessage.replace("expected [", "esperado[") .replace("but found [", "localizado[").replace("true", "sim").replace("false", "não");
			retStrBuf.append("<tr bgcolor=" + color + ">");
			
			/* suite e teste e parametro */
			retStrBuf.append("<td><b>");
			retStrBuf.append(testMethodName);
			retStrBuf.append("</b><br><i>");
			retStrBuf.append(testClassName);
			retStrBuf.append("</i><br>Parametro: ");
			retStrBuf.append(paramStr);
			retStrBuf.append("<br>");
			String aHyperlink = "\n<button onClick=\"reporterShowHide('" + testID + "')\">Exibe/Oculta detalhes</button>\n";
			retStrBuf.append(aHyperlink);
			retStrBuf.append("</td>");
			
			/*  tempo de execucao */
			retStrBuf.append("<td>");
			retStrBuf.append(executeTimeStr);
			retStrBuf.append("</td>");
			
			/*  data e hora de inicio. */
			retStrBuf.append("<td>");
			retStrBuf.append(startDateStr);
			retStrBuf.append("</td>");
			
			/* reporter  */
			retStrBuf.append("<td>");
			retStrBuf.append("<div class='log' style='display: none;' id=\"").append(testID).append("\">\n");
			retStrBuf.append("<font size=\"2\" face=\"courier\"");
			retStrBuf.append(reporterMessage);
			retStrBuf.append("</div>\n");
			retStrBuf.append("</font></td>");
			
			/* exception  */
			retStrBuf.append("<td>");
			retStrBuf.append(exceptionMessage);
			retStrBuf.append("</td>");
			
			retStrBuf.append("</tr>");

		}
		
		return retStrBuf.toString();
	}
	
}