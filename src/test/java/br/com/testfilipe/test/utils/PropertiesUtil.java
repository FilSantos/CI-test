package br.com.testfilipe.test.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesUtil {
	
	private static Properties prop = null;
	
	static{
		if (prop == null){
			try {
				prop = new Properties();
				prop.load(new FileInputStream("env.properties"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static String getProp(String propertie){	
		    System.out.println(prop.getProperty(propertie));
		    return prop.getProperty(propertie);
	}
}
