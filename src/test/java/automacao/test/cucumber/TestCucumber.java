package automacao.test.cucumber;

import org.junit.runner.RunWith;

import automacao.framework.runner.cucumber.BeforeSuite;
import automacao.framework.runner.cucumber.FeatureOverright;
import automacao.framework.runner.cucumber.ListenerCucumber;
import cucumber.api.CucumberOptions;


@CucumberOptions(
        features = "src/test/resources/features",
        glue = {""},
        //plugin = {"pretty", "html:relatorio"},
        plugin = {"pretty",},
        monochrome = true)

@RunWith(ListenerCucumber.class)

public class TestCucumber {
	
    @BeforeSuite
    public static void adjustData() throws Exception {
        FeatureOverright.overrideFeatureFiles("./src/test/resources/features");

    }
}

