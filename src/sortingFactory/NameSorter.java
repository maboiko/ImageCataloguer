package sortingFactory;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NameSorter implements Sorter{

	@Override
	public Map<String, List<File>> sort(ArrayList<File> imagesToSort) {
		
		Map<String, List<File>> map = imagesToSort.stream().collect(Collectors.groupingBy(
				(Function<File, String>) (File f) -> { char firstLetter = f.getName().toLowerCase().charAt(0);
					String result = isLatinOrCyrilic(firstLetter) ? String.valueOf(firstLetter) : "Other";
					return result;}));

		return map;
		
	}
	
	private static boolean isLatinOrCyrilic(char letter) {
		
		int unicodeIndexOfFirstSmallLatin = 97;
		int unicodeIndexOfLastSmallLatin = 122;
		int unicodeIndexOfFirstSmallCyrilic = 1072;
		int unicodeIndexOfLastSmallCyrilic = 1103;
		
		return (letter >= unicodeIndexOfFirstSmallLatin && letter <= unicodeIndexOfLastSmallLatin ||
				letter >= unicodeIndexOfFirstSmallCyrilic && letter <= unicodeIndexOfLastSmallCyrilic);
	}

	@Override
	public String getFeature() {
		return "name";
	}

}
