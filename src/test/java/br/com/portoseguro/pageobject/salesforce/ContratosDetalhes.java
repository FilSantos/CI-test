package br.com.portoseguro.pageobject.salesforce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.portoseguro.core.selenium.pageobject.BaseWebPage;

/**
 * @author everton cognizant
 */
public class ContratosDetalhes extends BaseWebPage {

    private static By PROPOSAL_TEXTBOX = By.xpath("//td[contains(text(),'Proposta')]/following-sibling::td/div");
    private static By INITIALVALIDITY_TEXTBOX = By.xpath ("//td[contains(text(),'Vigência inicial')]/following-sibling::td/div");
    private static By FINALVALIDITY_TEXTBOX = By.xpath ("//td[contains(text(),'Vigência final')]/following-sibling::td/div");
    private static By IDPARTNERAGREEMENT_TEXTBOX = By.xpath ("//td[contains(text(),'id Contrato Parceiro')]/following-sibling::td/div");
    private static By ISSUEDATE_TEXTBOX = By.xpath ("//td[contains(text(),'Data de emissão')]/following-sibling::td/div");
    private static By CONTRACTORSNUMBER_LINK = By.xpath ("//h3[text()='Contratantes do contrato']/../../../../../.././/th/a");
    private static By ACCOUNTNAME_LINK = By.xpath("//td[contains(text(),'Nome da conta')]/following-sibling::td//a");

    public ContratosDetalhes(WebDriver webDriver) {
        super(webDriver);
        // TODO Auto-generated constructor stub
    }
    @Override
    public boolean isDisplayed() {
        return false;
    }
    
    /** Retorna o número da proposta
     * @author everton cognizant
     * @return String
     * @throws Exception 
     */
    public String getProposal() throws Exception {
    	
    	WebElement proposal = searchElement.findElementBy(PROPOSAL_TEXTBOX, "Número da Proposta");

    	return proposal.getText();
    }
    /** Retorna a data de inicio de vigencia
     * @author everton cognizant
     * @return String
     * @throws Exception
     */
    public String getInitialValidity() throws Exception {

        WebElement date = searchElement.findElementBy(INITIALVALIDITY_TEXTBOX, "Data Inicio da vigência");

        return date.getText();
    }
    /** Retorna a data do final da vigencia
     * @author everton cognizant
     * @return String
     * @throws Exception
     */
    public String getFinalValidity() throws Exception {

        WebElement date = searchElement.findElementBy(FINALVALIDITY_TEXTBOX, "Data Final da vigência");

        return date.getText();
    }
    /** Retorna o id contrato parceiro
     * @author everton cognizant
     * @return String
     * @throws Exception
     */
    public String getIDPartner() throws Exception {

        WebElement text = searchElement.findElementBy(IDPARTNERAGREEMENT_TEXTBOX, "Id Contrato parceiro");

        return text.getText();
    }
    /** Retorna a data de emissão
     * @author everton cognizant
     * @return String
     * @throws Exception
     */
    public String getIssueDate() throws Exception {

        WebElement date = searchElement.findElementBy(ISSUEDATE_TEXTBOX, "Data de Emissão");

        return date.getText();
    }
    /** Clica no número do contratante
     * @author everton cognizant
     * @throws Exception
     */
    public void tapContactorsNumber() throws Exception {

        WebElement link = searchElement.findElementBy(CONTRACTORSNUMBER_LINK, "Número");

        command.click(link);
    }
    /** Clica no Nome da Conta
     * @author everton cognizant
     * @throws Exception
     */
    public void tapAccountName() throws Exception {

        WebElement link = searchElement.findElementBy(ACCOUNTNAME_LINK, "Nome da conta");

        command.click(link);
    }
}
