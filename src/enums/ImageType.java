package enums;

import java.util.ArrayList;

public enum ImageType {
	JPEG(".jpeg"), PNG(".png"), JPG(".jpg"), JPE(".jpe"), JFIF(".jfif");
	
	private String extension;
	
	ImageType(String format) {
		this.extension = format;
	}
	
	public String getExtension(){
		return this.extension;
	}
	
	public static ArrayList<String> getExtensionList() {
		ArrayList<String> extensionList = new ArrayList<>();
		
		for(ImageType type: ImageType.values()) {
			extensionList.add(type.getExtension());
		}
		
		return extensionList;
	}
}
