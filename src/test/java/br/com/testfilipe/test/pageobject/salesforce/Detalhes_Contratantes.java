package br.com.testfilipe.test.pageobject.salesforce;

import br.com.testfilipe.core.selenium.pageobject.BaseWebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @autor everton cognizant
 */

public class Detalhes_Contratantes extends BaseWebPage {
    private static By QUANTITYPARCEL_TEXTBOX = By.xpath("//div[@id='00N0j000000jbgQ_ileinner']");

    public Detalhes_Contratantes (WebDriver webDriver)
        super WebDriver
    // TODO Auto-generated constructor stub

    @Override
    public boolean isDisplayed() {
        return false;
    }
}
