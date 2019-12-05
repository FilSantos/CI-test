package automacao.framework.files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.apache.commons.io.FileUtils;

public class FilesAction {

	public static boolean deleteDir(File dir) {

		try {
			FileUtils.deleteDirectory(dir);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean createDir(File dir) {
		return dir.mkdir();
	}
	
	public boolean extractFromMainResources(String file) {
		try {
			InputStream initialStream = this.getClass().getResourceAsStream("/"+file);
			
			byte[] buffer = new byte[initialStream.available()];
		    initialStream.read(buffer);
		 
		    File targetFile = new File( System.getProperty("user.dir") + "/" + file);
		    targetFile.getParentFile().mkdirs();
		    OutputStream outStream = new FileOutputStream(targetFile,true);
		    outStream.write(buffer);
		    outStream.close();
		    return true;
		} catch (Exception e) {
			return true;
		}
		
	}
	
	public URL getFromMainResources(String file) {
		try {
			URL initialStream = this.getClass().getResource(file);
			return initialStream;
		} catch (Exception e) {
			return null;
		}
		
	}
	
}