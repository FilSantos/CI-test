package automacao.framework.iteraction.device.platform;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

public class AndroidPlatform extends AbstractAppiumPlatform {
	
	final static Logger logger = Logger.getLogger(AndroidPlatform.class);

	
	private Boolean clearData = false;
	private String binaryFile;
	private Locale locale;
	private String deviceId;
	private String androidVersion = "29";
	
	public static AndroidPlatform simulator(Boolean clearData,
			String binaryFile, Locale locale, String androidVersion) {
		return new AndroidPlatform(clearData, binaryFile, locale, null, androidVersion);
	}
	
	public static AndroidPlatform realDevice(Boolean clearData,
			String binaryFile, Locale locale, String deviceId) {
		return new AndroidPlatform(clearData, binaryFile, locale, deviceId, null);
	}
	
	public AndroidPlatform(Boolean clearData,
			String binaryFile, Locale locale, String deviceId, String androidVersion) {
		this.androidVersion = androidVersion;
		this.clearData = clearData;
		this.binaryFile = binaryFile;
		this.deviceId = deviceId;
		this.locale = locale;
	}
	
	@Override
	public WebDriver getLocalWebDriver() {
		logger.info("Iniciando o Appium local - Android");
		return super.getLocalWebDriver();
	}
	
	@Override
	public WebDriver getRemoteWebDriver(String host, int port) throws MalformedURLException  {
		logger.info("Starting Remote iPhone");
		return super.getRemoteWebDriver(host, port);
	}
	
	@Override
	protected DesiredCapabilities getDesiredCapabilities() {
		DesiredCapabilities cap = new DesiredCapabilities();
		File file = new File(binaryFile);
		
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
		//if (androidVersion != null) { cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, androidVersion); }
		cap.setCapability(MobileCapabilityType.APP, file.exists() ? file.getAbsolutePath() : binaryFile);
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
//		cap.setCapability(MobileCapabilityType.LANGUAGE, locale.getLanguage());
//		cap.setCapability(MobileCapabilityType.LOCALE, locale.toString());
		if (deviceId != null) {
			cap.setCapability(MobileCapabilityType.UDID, deviceId); 
		} else {
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, androidVersion);
		}
		
		cap.setCapability("commandTimeouts", "{\"findElement\": 40000, \"findElements\": 40000, \"setValue\": 40000,  \"acceptAlert\": 40000, \"dismissAlert\": 40000,  \"default\": 120000}");
		//cap.setCapability("commandTimeouts", "{\"findElement\": 40000, \"findElements\": 40000,");
		//cap.setCapability("preventWDAAttachments", "true");
		//cap.setCapability("simpleIsVisibleCheck", "true");
		//cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
		//cap.setCapability(MobileCapabilityType.FULL_RESET, clearData);
		
		//cap.setCapability(MobileCapabilityType.NO_RESET, true);
		//cap.setCapability(MobileCapabilityType.CLEAR_SYSTEM_FILES,  false);
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 40000);
		return cap;
	}

}
