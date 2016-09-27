package sortingFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import utilities.*;

public class ColorSorter implements Sorter{

	@Override
	public Map<String, List<File>> sort(ArrayList<File> imagesToSort) {
		
		Map<String, List<File>> map = imagesToSort.stream().collect(Collectors.groupingBy(
				(Function<File, String>) (File f) -> {BufferedImage image = FromFileCreator.createBufferedImage(f);
				String color = ColorRecognizer.processImage(image, f.getName());
				return color;}));
	
		return map;
		
	}

	@Override
	public String getFeature() {
		return "color";
	}

}
