package br.com.testfilipe.test.pageobject.salesforce;

import br.com.testfilipe.core.selenium.pageobject.BaseWebPage;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author everton cognizant
 */
public class ContaSeguradoEndereco extends BaseWebPage {
    private static By CEP_TEXTBOX = By.xpath("//tr//td/label[contains(text(),'CEP')]/../..//input[@type='text']");
    private static By STREET_TEXTAREA = By.xpath("//tr//td/label[contains(text(),'Logradouro')]/../..//td//textarea");
    private static By COMPLEMENT_TEXTBOX =  By.xpath("//tr//td/label[contains(text(),'Complemento')]/../following-sibling::td[1]/input");
    private static By RESIDENCIALNUMBER_TEXTBOX = By.xpath("//tr//td/label[contains(text(),'Número')]/../..//div[@class='requiredBlock']/../input");
    private static By DISTRICT_TEXTBOX = By.xpath("//tr//td/label[contains(text(),'Bairro')]/../following-sibling::td[1]/input");
    private static By CITY_TEXTBOX = By.xpath("//tr//td/label[contains(text(),'Cidade')]/../..//following-sibling::input[1]");
    private static By STATE_SELECT = By.xpath("//tr//td/label[contains(text(),'Estado')]/../..//select[@id]");

    /**
     * Default options to Page Objects
     *
     * @param webDriver
     */
    public ContaSeguradoEndereco(WebDriver webDriver) {

        super(webDriver);
    }

    @Override
    public boolean isDisplayed() {
        // TODO Auto-generated method stub
        return false;
    }

}