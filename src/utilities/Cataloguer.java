package utilities;

import java.io.*;
import java.nio.file.Files;
import java.util.*;


public class Cataloguer {
	
	public static void createCatalogue(String feature, String destDirectory, Map<String, List<File>> sortedImages) throws IOException {
		
		String destFolder = createDirectoryName(destDirectory, feature + "-sorted");
		
		for(Map.Entry<String, List<File>> imageGroup: sortedImages.entrySet()) {
			String subfolder = createDirectoryName(destFolder, imageGroup.getKey());
			new File(subfolder).mkdirs();
			
			for(File image: imageGroup.getValue()) {
				copyFiles(subfolder, image);
				
				File test = new File(createDirectoryName(subfolder,image.getName()));
				test.setExecutable(true);
				
			}
		}
		
		System.out.println("New catalogues created");
		
	}
	

	private static String createDirectoryName(String destDirectory, String innerDirectory) {
		String directoryName = new StringBuilder(destDirectory).append(File.separator)
															.append(innerDirectory).toString();
		
		return testDirectoryIfNotExists(directoryName) ? directoryName: (directoryName + ".1");
	}
	
	private static boolean testDirectoryIfNotExists(String path) {
		File testFile = new File(path);
		
		return Files.notExists(testFile.toPath());
	}
	
	private static void copyFiles(String path, File source) throws IOException {
		
		File dest = new File(createDirectoryName(path,source.getName()));
		
		try (InputStream is = new BufferedInputStream(new FileInputStream(source)); 
				OutputStream os = new BufferedOutputStream(new FileOutputStream(dest)))
		{
			
			byte[] buffer = new byte[1024];
			int length;
			while((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
				os.flush();
			}
		}
	}

}
