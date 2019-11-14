package automacao.step;

import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

public class TestSteps {
	@Dado("^scenario data$")
	public void scenario_data() {
	    // Write code here that turns the phrase above into concrete actions
	   // throw new PendingException();
	}

	@Quando("^executed from Runner Class\\.$")
	public void executed_from_Runner_Class() {
	    // Write code here that turns the phrase above into concrete actions
	  //  throw new PendingException();
	}

	@Entao("^UserName and Password shows on console from Examples \"([^\"]*)\" and \"([^\"]*)\"$")
	public void username_and_Password_shows_on_console_from_Examples_and(String arg1, String arg2) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
	}

}