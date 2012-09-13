package org.mat.samples.xslt.util;

public class FormatDecimal {

	public static String convertDecimalToString(String stringToConvert,
			int numberFloor, int numberDecimal) {
		
		// Define variable
		StringBuffer stringLeftZero = new StringBuffer();
		StringBuffer stringRightZero = new StringBuffer();
		String splitString[];
		String result = "";
		
		String intPart = "";
		String decPart = "";
		
		if(stringToConvert == null || stringToConvert.equals("")){
			
			for(int i =0; i<numberFloor;i++){
				
				result+="0";
			}
			
			for(int i =0; i<numberDecimal;i++){

				result+="0";
			}
		}else{

			if (stringToConvert.substring(0, 1).equals("-")) {
				stringToConvert = stringToConvert.substring(1);
			}
			
			if(!stringToConvert.contains(".")){
				
				intPart = stringToConvert;
				decPart = "";
				
			}else{
				
				splitString = stringToConvert.split("\\.");
				intPart = splitString[0];
				decPart = splitString[1];
			}
			
			// Take length of integer and decimal
			int lengthIntPart = intPart.length();
			int lengthDecPart = decPart.length();
			
			// Find number of zero to add
			int numberIntZero = numberFloor - lengthIntPart;
			int numberDecZero = numberDecimal - lengthDecPart;
			
			// Add zero to integer part
			for (int i = 0; i < numberIntZero; i++) {
				stringLeftZero.append("0");
			}
			
			// Add zero to decimal part
			for (int i = 0; i < numberDecZero; i++) {
				stringRightZero.append("0");
			}
			
			// Build the result
			result = stringLeftZero.toString() + intPart + decPart
					+ stringRightZero.toString();	
		}
		
		return result;
	}
	
}