package utilities;

import java.awt.image.BufferedImage;
import java.util.*;

import enums.Colors;
import exceptions.AttributeNotFoundException;

public class ColorRecognizer {
	
	private static ArrayList<Double> valuesRGB = new ArrayList<>();
	private static ArrayList<Integer> valuesHSV = new ArrayList<>();
	private static String fileName = null;
	
	public static String processImage(BufferedImage image, String file) {
		fileName = file;
		prepareImage(image);
		toHSV();
		String result = toColorName();
		
		clearResources();
		
		return result;
	}
	
	private static void prepareImage(BufferedImage image) {
		  
			int height = image.getHeight();
			int width = image.getWidth();
			
			int totalR =0, totalG =0, totalB =0;
			
			int[] pixelRGB = image.getRGB(0, 0, width, height, null, 0, width);
			
			for(int i=0; i < pixelRGB.length; i++) {
				int color = pixelRGB[i];
				
				totalR+= (color & 0xff0000) >> 16;
				totalG+= (color & 0xff00) >> 8;
				totalB+= (color & 0xff);
			}
			
			valuesRGB.add(((double) totalR/width/height/255));
			valuesRGB.add(((double) totalG/width/height/255));
			valuesRGB.add(((double) totalB/width/height/255));
			
		}

	private static void toHSV() {
		
		if(valuesRGB.isEmpty()) throw new AttributeNotFoundException("File " + fileName + " can not be sorted with a color!");
		
		ArrayList<Double> copyList = new ArrayList<>(valuesRGB);
		Collections.sort(copyList);
		
		double maxValue = copyList.get(2);
		double minValue = copyList.get(0);
		double delta = maxValue - minValue;
		
		//Hue
		int h = defineHue(maxValue, delta);
		//Saturation
		int s = defineSaturation(maxValue, delta);
		//Value
		int v = (int) Math.round(maxValue * 100);
		
		valuesHSV.add(h);
		valuesHSV.add(s);
		valuesHSV.add(v);
		
	}
	
	private static String toColorName() {
		if(valuesHSV.isEmpty()) throw new AttributeNotFoundException("File " + fileName + " can not be sorted with a color!");
		
		String colorName;
		
		int hue = valuesHSV.get(0);
		int saturation = valuesHSV.get(1);
		int value = valuesHSV.get(2);
		
		if(value < 10) {
			colorName = Colors.BLACK.getColorName();
		}
		
		else if(value > 85 && saturation < 10) {
			colorName = Colors.WHITE.getColorName();
		}
		
		else if(saturation < 10) {
			colorName = Colors.GRAY.getColorName();
		}
		
		else
		{
			if(hue >= 351 || hue < 30) 
				colorName = Colors.RED.getColorName();
			
			else if(hue >= 270 && hue < 351) 
				colorName = Colors.PINK.getColorName();
			
			else if(hue >= 61 && hue < 270) 
			{
				if(valuesRGB.get(1) > valuesRGB.get(2))
					colorName = Colors.GREEN.getColorName();
				else
					colorName = Colors.BLUE.getColorName();	
			}
			
			else if(hue >= 30 && hue <45 && value <= 50)
				colorName = Colors.BROWN.getColorName();
			
			else colorName = Colors.YELLOW.getColorName();		
		}
		
		return colorName;
	}
	
	private static int defineHue(double maxValue, double delta) {
		int hue;
		if(delta != 0) 
		{
			double firstResult;
			
			if(maxValue == valuesRGB.get(0)) {
				firstResult = (valuesRGB.get(1) - valuesRGB.get(2))/delta;
			}
			else if(maxValue == valuesRGB.get(1)) {
				firstResult = (valuesRGB.get(2) - valuesRGB.get(0))/delta + 2;
			}
			else {
				firstResult = (valuesRGB.get(0) - valuesRGB.get(1))/delta + 2;
			}
			
			hue = (int) Math.round(firstResult *60);
			
			if(hue < 0) hue+= 360;
			
		}
		else
			hue = 0;
		
		return hue;
	}
	
	private static int defineSaturation(double maxValue, double delta) {
		
		int saturation = (maxValue == 0)? 0 : (int) Math.round(100 * delta / maxValue);
		
		return saturation;
	}
	
	private static void clearResources() {
		valuesRGB.clear();
		valuesHSV.clear();
		fileName = null;
	}

}
