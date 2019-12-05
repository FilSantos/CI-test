package automacao.test.cucumber;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.runner.RunWith;

import automacao.framework.runner.LogConstants;
import automacao.framework.runner.cucumber.BeforeSuite;
import automacao.framework.runner.cucumber.FeatureOverright;
import automacao.framework.runner.cucumber.ListenerCucumber;
import cucumber.api.CucumberOptions;


@CucumberOptions(
        features = "features",
        glue = {""},
        plugin = {"pretty", "html:relatorio"},
        //plugin = {"pretty",},
        tags = {"@BRQ"},
        monochrome = true)

@RunWith(ListenerCucumber.class)

public class TestCucumber {
	
	final static Logger LOGGER = Logger.getLogger(TestCucumber.class);
	
    @BeforeSuite
    public static void adjustData() throws Exception {
    	PropertyConfigurator.configure(LogConstants.PROPERTIES);
        FeatureOverright.overrideFeatureFiles("./features");

    }
//    @After
//    public static void unloadDriver() throws Exception{
//    	
//    	ConstantsMobile.unloadScenario();
//    	
//    }
}

