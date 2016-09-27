package sortingFactory;

import java.io.File;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import utilities.FromFileCreator;

public class MonthYearSorter implements Sorter{

	@Override
	public Map<String, List<File>> sort(ArrayList<File> imagesToSort) {
		SimpleDateFormat yearFormat = new SimpleDateFormat("MMMM, yyyy", new Locale("en"));

		Map<String, List<File>> map = imagesToSort.stream().collect(Collectors.groupingBy(
				(Function<File, String>) (File f) -> {FileTime creationTime = FromFileCreator.createFileTime(f);
				String creationYear = yearFormat.format(creationTime.toMillis());
				return creationYear;}));

		return map;
		
	}

	@Override
	public String getFeature() {
		return "month_of_year";
	}

}
