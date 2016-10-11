package sortingFactory;

import exceptions.SortingFeatureNotFoundException;

public class SorterFactory {
	
	public static Sorter getSorter(String feature) {
		if(feature == null)
			throw new SortingFeatureNotFoundException("No feature to sort");
		
		if(feature.equalsIgnoreCase("color"))
			return new ColorSorter();
		else if(feature.equalsIgnoreCase("extension"))
			return new ExtensionSorter();
		else if(feature.equalsIgnoreCase("monthyear"))
			return new MonthYearSorter();
		else if(feature.equalsIgnoreCase("name"))
			return new NameSorter();
		else if(feature.equalsIgnoreCase("orientation"))
			return new OrientationSorter();
		else if(feature.equalsIgnoreCase("year"))
			return new YearSorter();
		
		return null;
	}

}
