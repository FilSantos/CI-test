package automacao.framework.selenium.platfom;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class ConfigProxy {
	
	public static void main(String[] args) {
		Proxy proxy = new Proxy();
		proxy.setProxyAutoconfigUrl("proxy");
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.PROXY, proxy);
		
		System.setProperty("webdrive.chrome.driver", "C:\\Users\\793508\\git\\QualityAssuranceVida\\web\\src\\test\\resources\\chromeDriver\\win_chromedriver.exe");
		
		ChromeDriver d = new ChromeDriver(cap);
		d.get("test.salesforce.com");
	}

}
