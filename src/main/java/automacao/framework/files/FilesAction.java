package automacao.framework.files;

import java.io.File;
import java.io.IOException;

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
}