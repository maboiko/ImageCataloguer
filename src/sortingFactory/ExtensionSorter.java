package sortingFactory;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import utilities.FromFileCreator;

public class ExtensionSorter implements Sorter{

	@Override
	public Map<String, List<File>> sort(ArrayList<File> imagesToSort) {
		
		Map<String, List<File>> map = imagesToSort.stream().collect(Collectors.groupingBy(
				(Function<File, String>) (File f) -> FromFileCreator.getExtension(f).substring(1)));

		return map;
		
	}

	@Override
	public String getFeature() {
		return "extension";
	}

}
