package br.com.testfilipe.test.pageobject.salesforce;

import br.com.testfilipe.core.selenium.pageobject.BaseWebPage;
import com.sun.corba.se.impl.encoding.CodeSetConversion;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ContaDetalhes extends BaseWebPage {

    private static By ACCOUNTNAME_TEXTBOX = By.xpath("//td[contains(text(),'Nome da conta')]/following-sibling::td[1]/div");
    private static By SEX_TEXTBOX = By.xpath("//td[contains(text(),'Sexo')]/following-sibling::td[1]/div");
    private static By DATEOFBIRD_TEXBOX = By.xpath("//td[contains(text(),'Data de nascimento')]/following-sibling::td[1]/div");
    private static By CPF_TEXBOX = By.xpath("//td[contains(text(),'CPF')]/following-sibling::td[1]/div");
    private static By MARITALSTATUS_TEXBOX = By.xpath("//td[contains(text(),'Estado civil')]/following-sibling::td[1]/div");
    private static By CELLPHONENUM_TEXTBOX = By.xpath("//td[contains(text(),'Celular')]/following-sibling::td[1]/div");
    private static By COMMERCPHONENUM_TEXTBOX = By.xpath("//td[contains(text(),'Telefone comercial')]/following-sibling::td[1]/div");
    private static By PHONENUMBER_TEXTBOX = By.xpath("//td[contains(text(),'Telefone residencial')]/following-sibling::td[1]/div");
    private static By EDITADRESS_LINK = By.xpath("//h3[text()='Endereços']/../../../../../..//tr//a[@class='actionLink'][contains(text(), 'Editar')]");

    public ContaDetalhes (WebDriver webdriver){
        super (webDriver);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean isDisplayed() {
        return false;
    }
}
