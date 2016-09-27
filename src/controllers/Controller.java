package controllers;

import java.io.*;
import java.util.ArrayList;

import sortingFactory.*;
import utilities.*;

public class Controller {
	
	public static ArrayList<File> findImagesToSort(String path) {
		
		ArrayList<File> result = ImageSeeker.findImages(path);
		
		return (result.isEmpty()) ? null : result;
	}
	
	public static void processSorting(String feature, String destDirectory, ArrayList<File> images) throws IOException {
		Sorter mySorter = SorterFactory.getSorter(feature);
		Cataloguer.createCatalogue(mySorter.getFeature(), destDirectory, mySorter.sort(images));
		
	}

}
