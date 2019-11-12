package automacao.test.cucumber;

import org.junit.runner.RunWith;

import automacao.framework.cucumber.BeforeSuite;
import automacao.framework.cucumber.CustomCucumberRunner;
import automacao.framework.cucumber.FeatureOverright;
import cucumber.api.CucumberOptions;


@CucumberOptions(
        features = "src/test/resources/features",
        glue = {""},
        plugin = {"pretty", "html:cucumber",
                "junit:junit"},
        monochrome = true)

@RunWith(CustomCucumberRunner.class)

public class RunnerTest {
	
	
    @BeforeSuite
    public static void test() throws Exception {
        FeatureOverright.overrideFeatureFiles("./src/test/resources/features");

    }
}

