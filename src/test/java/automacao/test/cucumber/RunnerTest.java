package automacao.test.cucumber;

import org.junit.runner.RunWith;

import automacao.framework.runner.cucumber.BeforeSuite;
import automacao.framework.runner.cucumber.CustomCucumberRunner;
import automacao.framework.runner.cucumber.FeatureOverright;
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

