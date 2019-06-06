package br.com.portoseguro.pageobject.salesforce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.portoseguro.core.selenium.pageobject.BaseWebPage;

public class ContaDetalhes extends BaseWebPage {

    public ContaDetalhes(WebDriver webDriver) {
		super(webDriver);
	}

	private static By ACCOUNTNAME_TEXTBOX = By.xpath("//td[contains(text(),'Nome da conta')]/following-sibling::td[1]/div");
    private static By SEX_TEXTBOX = By.xpath("//td[contains(text(),'Sexo')]/following-sibling::td[1]/div");
    private static By DATEOFBIRTH_TEXBOX = By.xpath("//td[contains(text(),'Data de nascimento')]/following-sibling::td[1]/div");
    private static By CPF_TEXBOX = By.xpath("//td[contains(text(),'CPF')]/following-sibling::td[1]/div");
    private static By MARITALSTATUS_TEXBOX = By.xpath("//td[contains(text(),'Estado civil')]/following-sibling::td[1]/div");
    private static By CELLPHONENUM_TEXTBOX = By.xpath("//td[contains(text(),'Celular')]/following-sibling::td[1]/div");
    private static By COMMERCPHONENUM_TEXTBOX = By.xpath("//td[contains(text(),'Telefone comercial')]/following-sibling::td[1]/div");
    private static By PHONENUMBER_TEXTBOX = By.xpath("//td[contains(text(),'Telefone residencial')]/following-sibling::td[1]/div");
    private static By EDITADRESS_LINK = By.xpath("//h3[text()='Endereços']/../../../../../..//tr//a[@class='actionLink'][contains(text(), 'Editar')]");


    @Override
    public boolean isDisplayed() {
        return false;
    }

    /**Retorna o nome do contratante
     * @author everton cognizant
     * @return String
     * @throws Exception
     */
    public String getAccountName () throws Exception{

        WebElement text = searchElement.findElementBy (ACCOUNTNAME_TEXTBOX, "Nome da conta");

        return text.getText();
    }

    /** Retorna o sexo do contratante
     * @author everton cognizant
     * @return String
     * @throws Exception
     */
    public String getSex () throws Exception{

        WebElement text = searchElement.findElementBy (SEX_TEXTBOX, "sexo");

        return text.getText();
    }

    /**Retorna a data de nascimento
     * @author everton cognizant
     * @return String
     * @throws Exception
     */
    public String getDateOfBirth () throws Exception{

        WebElement text = searchElement.findElementBy(DATEOFBIRTH_TEXBOX, "Data de nascimento");

        return text.getText();
    }

    /**Retorna o cpf
     * @author everton cognizant
     * @return String
     * @throws Exception
     */
    public String getCPF () throws Exception{

        WebElement text = searchElement.findElementBy(CPF_TEXBOX, "CPF");

        return text.getText();
    }

    /**Retorna o estado civil
     * @author everton cognizant
     * @return String
     * @throws Exception
     */
    public String getMaritalStatus () throws Exception{

        WebElement text = searchElement.findElementBy(MARITALSTATUS_TEXBOX, "Estado civil");

        return text.getText();
    }

    /**Retorna número do celular
     * @author everton cognizant
     * @return String
     * @throws Exception
     */
    public String getCellphoneNumber () throws Exception{

        WebElement text = searchElement.findElementBy(CELLPHONENUM_TEXTBOX, "Número do celular");

        return text.getText();
    }

    /**Retorna o telefone comercial
     * @author everton cognizant
     * @return String
     * @throws Exception
     */
    public String getCommercialNumber () throws Exception{

        WebElement text = searchElement.findElementBy(COMMERCPHONENUM_TEXTBOX, "Telefone Comercial");

        return text.getText();
    }

    /**Retorna o telefone residencial
     * @author everton cognizant
     * @return String
     * @throws Exception
     */
    public String getPhoneNumber () throws Exception{

        WebElement text = searchElement.findElementBy(PHONENUMBER_TEXTBOX, "Telefone residencial");

        return text.getText();
    }

    /**Clica em Editar (aba Endereços)
     * @author everton cognizant
     * @throws Exception
     */
    public void tapEditAdress () throws Exception{

        WebElement link = searchElement.findElementBy(EDITADRESS_LINK, "Endereços - EDITAR");

        command.click(link);
    }


}
