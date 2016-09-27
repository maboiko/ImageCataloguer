package utilities;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import enums.ImageType;

public class ImageSeeker {
	
	public static ArrayList<File> findImages(String folderPath){
		
		ArrayList<File> imageList = null;
		
		File folderToSearch = new File(folderPath);
		ArrayList<File> files = new ArrayList<>(Arrays.asList(folderToSearch.listFiles()));
		imageList = (ArrayList<File>) files.stream()
				.filter(s -> isAppropriateType(s))
				.collect(Collectors.toList());
		
		return imageList;
	}
	
	private static boolean isAppropriateType(File file) {
		boolean result = false;
		
		if(file.isFile()) {
		String extension = FromFileCreator.getExtension(file);
		result = ImageType.getExtensionList().stream().filter(s -> s.equals(extension)).count() == 0 ? false: true;
		}
		
		return result;
		
	}

}
