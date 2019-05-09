package br.com.testfilipe.test.pageobject.salesforce;

import br.com.testfilipe.core.selenium.pageobject.BaseWebPage;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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

    /**Retorna o CEP
     * @author everton cognizant
     * @return String
     * @throws Exception
     */
    public String getCEP () throws Exception{

        WebElement text = searchElement.findElementBy(CEP_TEXTBOX "CEP");

        return text.getText()
    }

    /**Retorna o logradouro
     * @author everton cognizant
     * @return String
     * @throws Exception
     */
    public String getStreet () throws Exception{

        WebElement text = searchElement.findElementBy(STREET_TEXTAREA "Logradouro");

        return text.getText()
    }

    /**Retorna o complemento
     * @author everton cognizant
     * @return String
     * @throws Exception
     */
    public String getComplement () throws Exception{

        WebElement text = searchElement.findElementBy(COMPLEMENT_TEXTBOX "Complemento");

        return text.getText()
    }

    /**Retorna o número da residencia
     * @author everton cognizant
     * @return String
     * @throws Exception
     */
    public String getResidencialNumber () throws Exception{

        WebElement text = searchElement.findElementBy(RESIDENCIALNUMBER_TEXTBOX "Número da residencia");

        return text.getText()
    }

    /**Retorna o bairro
     * @author everton cognizant
     * @return String
     * @throws Exception
     */
    public String getDistrict () throws Exception{

        WebElement text = searchElement.findElementBy(DISTRICT_TEXTBOX "Bairro");

        return text.getText()
    }

    /**Retorna a cidade
     * @author everton cognizant
     * @return String
     * @throws Exception
     */
    public String getCity () throws Exception{

        WebElement text = searchElement.findElementBy(CITY_TEXTBOX "Cidade");

        return text.getText()
    }

    /**Retorna o estado
     * @author everton cognizant
     * @return String
     * @throws Exception
     */
    public String getState () throws Exception{

        WebElement text = searchElement.findElementBy(STATE_SELECT "Estado");

        return text.getText()
    }

}