package br.com.testfilipe.test.pageobject.salesforce;

import br.com.testfilipe.core.selenium.pageobject.BaseWebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author everton cognizant
 */
public class ContratosDetalhes extends BaseWebPage {

    private static By PROPOSAL_TEXTBOX = By.xpath("//td[contains(text(),'Proposta')]/following-sibling::td/div");
    private static By INITIALVALIDITY_TEXTBOX = By.xpath ("//td[contains(text(),'Vigência inicial')]/following-sibling::td/div");
    private static By FINALVALIDITY_TEXTBOX = By.xpath ("//td[contains(text(),'Vigência final')]/following-sibling::td/div");
    private static By IDPARTNERAGREEMENT_TEXTBOX = By.xpath ("//td[contains(text(),'id Contrato Parceiro')]/following-sibling::td/div");
    private static By ISSUEDATE_TEXTBOX = By.xpath ("//td[contains(text(),'Data de emissão')]/following-sibling::td/div");
    private static By CONTRACTORSNUMBER_LINK = By.xpath ("//a[text()='00005752']");

    public ContratosDetalhes(WebDriver webDriver) {
        super(webDriver);
        // TODO Auto-generated constructor stub
    }
    @Override
    public boolean isDisplayed() {
        return false;
    }
}
