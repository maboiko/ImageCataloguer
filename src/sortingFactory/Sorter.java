package sortingFactory;

import java.io.File;
import java.util.*;

public interface Sorter {
	
	public Map<String, List<File>> sort(ArrayList<File> imagesToSort);
	
	public String getFeature();

}
