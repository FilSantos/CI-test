package automacao.framework.iteraction.device.platform;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

public class IPadPlatform extends AbstractAppiumPlatform {
	
	final static Logger logger = Logger.getLogger(IPadPlatform.class);

	private static String XCODE_CONFIG_FILE_PATH = "./resources/selenium/webdriver.xcconfig";
	
	private Boolean clearData;
	private String binaryFile;
	private Locale locale;
	private String deviceId;
	private String iOSVersion= "11.2";
	
	public static IPadPlatform simulator(Boolean clearData,
			String binaryFile, Locale locale, String iOSVersion) {
		return new IPadPlatform(clearData, binaryFile, locale, null, iOSVersion);
	}
	
	public static IPadPlatform realDevice(Boolean clearData,
			String binaryFile, Locale locale, String deviceId) {
		return new IPadPlatform(clearData, binaryFile, locale, deviceId, null);
	}
	
	public IPadPlatform(Boolean clearData,
			String binaryFile, Locale locale, String deviceId, String iOSVersion) {
		this.iOSVersion = iOSVersion;
		this.clearData = clearData;
		this.binaryFile = binaryFile;
		this.deviceId = deviceId;
		this.locale = locale;
	}
	
	@Override
	public WebDriver getLocalWebDriver() {
		logger.info("Starting Local iPad");
		return super.getLocalWebDriver();
	}
	
	@Override
	public WebDriver getRemoteWebDriver(String host, int port) throws MalformedURLException {
		logger.info("Starting Remote iPad");
		return super.getRemoteWebDriver(host, port);
	}
	
	@Override
	protected DesiredCapabilities getDesiredCapabilities() {
		DesiredCapabilities cap = new DesiredCapabilities();
		
		File file = new File(binaryFile);
		
		
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
		if (iOSVersion != null) { cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, iOSVersion); }
		cap.setCapability(MobileCapabilityType.APP, file.exists() ? file.getAbsolutePath() : binaryFile);
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "iPad Air 2");
		cap.setCapability(MobileCapabilityType.LANGUAGE, locale.getLanguage());
		cap.setCapability(MobileCapabilityType.LOCALE, locale.toString());
		if (deviceId != null) { cap.setCapability(MobileCapabilityType.UDID, deviceId); }
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
		cap.setCapability("xcodeConfigFile", XCODE_CONFIG_FILE_PATH); 
		cap.setCapability(MobileCapabilityType.FULL_RESET, clearData);
		cap.setCapability(MobileCapabilityType.NO_RESET, true);
		if (deviceId != null) { cap.setCapability(MobileCapabilityType.ORIENTATION, "LANDSCAPE "); }
		cap.setCapability(MobileCapabilityType.CLEAR_SYSTEM_FILES, file.exists() ? true : false);
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 720);
		return cap;
	}

}
