package utilities;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

import javax.imageio.ImageIO;

import exceptions.SortingFeatureNotFoundException;

public interface FromFileCreator {
	
	public static FileTime createFileTime(File file) {
		FileTime creationTime = null;
		
		try {
			creationTime = Files.readAttributes(Paths.get(file.getAbsolutePath()), BasicFileAttributes.class).creationTime();
		} catch (IOException e) {
			throw new SortingFeatureNotFoundException("File " + file.getName() + " can not be sorted!");
		}
		
		return creationTime;
	}
	
	public static BufferedImage createBufferedImage(File file) {
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			throw new SortingFeatureNotFoundException("File " + file.getName() + " can not be sorted!");
		}
		
		return image;
	}
	
	public static String getExtension(File file) {
		String result = null;
		
		try{
			result = file.getName().toLowerCase().substring(file.getName().lastIndexOf('.'));
		}
		catch(IndexOutOfBoundsException e){
			e.printStackTrace();
		}
		
		return result;
	}

}
