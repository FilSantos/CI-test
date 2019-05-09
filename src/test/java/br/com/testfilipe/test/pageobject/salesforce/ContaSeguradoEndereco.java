package br.com.testfilipe.test.pageobject.salesforce;

import br.com.testfilipe.core.selenium.pageobject.BaseWebPage;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.openqa.selenium.WebDriver;

/**
 * @author everton cognizant
 */
public class ContaSeguradoEndereco extends BaseWebPage {

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