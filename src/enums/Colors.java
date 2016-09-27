package enums;

public enum Colors {
	WHITE("white"), BLACK("black"), GRAY("gray"), RED("red"), PINK("pink"), 
	BLUE("blue"), GREEN("green"), YELLOW("yellow"), BROWN("brown");
	
	private String colorName;
	
	private Colors(String colorName){
		this.colorName = colorName;
	}
	
	public String getColorName() {
		return this.colorName;
	}

}
