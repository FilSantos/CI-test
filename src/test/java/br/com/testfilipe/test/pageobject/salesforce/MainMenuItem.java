package br.com.testfilipe.test.pageobject.salesforce;

import org.openqa.selenium.By;

/**
 * @author filipe
 * Represents the items that appear on salesforce tab bar after the user logs in
 */
public enum MainMenuItem {
	

	INICIO_TAB("Inicio", By.xpath("//li[@id='home_Tab']/a")), 	
	CALENDARIO_TAB("Calendário", By.xpath("//span[text()='Calendário']/..")), 	
	CONTAS_TAB("Contas", By.xpath("//span[text()='Contas']/..")),
	ORCAMENTOS_TAB("Orçamentos", By.xpath("//span[text()='Orçamentos']/..")),
	PROPOSTAS_TAB("Propostas", By.xpath("//li[@id='Quote_Tab']/a")),
	CASOS_TAB("Casos", By.xpath("//span[text()='Casos']/..")),
	CONTRATOS_TAB("Casos", By.xpath("//span[text()='Contratos']/..")),
	PRODUTOS_TAB("Produtos", By.xpath("//span[text()='Produtos']/..")),
	FATURAS_TAB("Faturas", By.xpath("//span[text()='Faturas']/..")),
	CONTABILIZACOESDEVALORES_TAB("Contabilizações de valores", By.xpath("//span[text()='Contabilizações de valores']/..")),
	CONTROLESDEINTEGRACAO_TAB("Controles de integração", By.xpath("//span[text()='Controles de integração']/..")),
	RELATORIOS_TAB("Relatórios", By.xpath("//span[text()='Relatórios']/..")), 
	PAINEIS_TAB("Painéis", By.xpath("//span[text()='Painéis']/..")),
	CRITICASDEINTEGRACOES_TAB("Críticas de integrações", By.xpath("//span[text()='Críticas de integrações']/..")),
	TITULOSINTEGRACAO_TAB("Títulos Integração", By.xpath("//span[text()='Títulos Integração']/..")),
	MAIS_TAB("Mais", By.xpath("//one-app-nav-bar-menu-button//span[text()='Mais']"));

	/**
	 * Name to use for identifying the tab batem in the report
	 */
	private final String reportName;
	
	/**
	 * Strategy for locating the item on screen.
	 */
	private final By locator;

    MainMenuItem(String reportName, By locator) {
		this.reportName = reportName;
		this.locator = locator;
	}
    
    public By getLocator() {
        return locator;
    }
    
    public String getReportName() {
        return reportName;
    }
    public By getAlternativeLocator() {
    	return By.xpath(String.format("//one-app-nav-bar-menu-item//span[text()='%s']", reportName));
    }
    
}
