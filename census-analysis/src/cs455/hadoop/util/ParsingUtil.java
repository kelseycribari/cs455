package cs455.hadoop.util;

/*
 * Author: Kelsey Cribari 
 * Date: 3-30-17
 * 
 * Class designed to assist with parsing records. 
 */

public class ParsingUtil {
	
	public static String state(String text) {
		//Starts at index 9 and field size is 2
		String state = text.substring(8, 10);
		return state; 
	}
	
	public static String summaryLevel(String text) {
		//Starts at index 11 and field size is 3
		String summaryLevel = text.substring(10, 13); 
		return summaryLevel; 
	}
	
	public static Long logicalRecordNumber(String text) {
		//Starts at index 19 and field size is 6
		Long logicalRecordNumber = Long.parseLong(text.substring(18, 24));
		return logicalRecordNumber; 
		
	}
	
	public static Long logicalRecordPartNumber(String text) {
		//Starts at index 25 and field size is 4
		Long logicalRecordPartNumber = Long.parseLong(text.substring(24, 28));
		return logicalRecordPartNumber; 
	}
	
	public static Long totalPartsInRecord(String text) {
		//Starts at index 29 and field size is 4
		Long totalPartsInRecord = Long.parseLong(text.substring(28, 32));
		return totalPartsInRecord; 
	}

}
