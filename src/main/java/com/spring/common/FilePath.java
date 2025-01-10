package com.spring.common;

import org.springframework.stereotype.Component;
import com.spring.constant.Constant;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;



@Component
public class FilePath {
	
	public String getPathToUploadFile(String Type) { // Use
		String pathtoUploads;
		if (Type.equalsIgnoreCase(Constant.invoiceImage)) {
			pathtoUploads = Constant.baseDocLocation + Constant.invoiceImage;
		} else if (Type.equalsIgnoreCase(Constant.receipt)) {
			pathtoUploads = Constant.baseDocLocation + Constant.receipt;
		} else {
			pathtoUploads = Constant.baseDocLocation + Constant.defaultPath;
		}

		if (!new File(pathtoUploads).exists()) {
			File dir = new File(pathtoUploads);
			dir.mkdirs();
		}
		Path path = Paths.get(pathtoUploads);
		return path.toString();
	}
}
