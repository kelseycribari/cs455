package cs455.hadoop.mappers;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import cs455.hadoop.records.AverageRoomsRecord;
import cs455.hadoop.util.ParsingUtil;

public class AverageRoomsMapper extends Mapper<LongWritable, Text, Text, AverageRoomsRecord>{

	
	@Override
	protected void map(LongWritable keyIn, Text valueIn, Context context) throws IOException, InterruptedException {
		String text = valueIn.toString(); 
		String summaryLevel = ParsingUtil.summaryLevel(text);
		
		if (summaryLevel.equals("100")) {
			String state = ParsingUtil.state(text); 
			Long logicalRecordPartNumber = ParsingUtil.logicalRecordPartNumber(text);
			Long totalPartsInRecord = ParsingUtil.totalPartsInRecord(text);
			
			if (logicalRecordPartNumber.equals(totalPartsInRecord)) {
				
				AverageRoomsRecord avgRoomsRecord = new AverageRoomsRecord(); 
				avgRoomsRecord.setLogicalRecordPartNumber(logicalRecordPartNumber);
				avgRoomsRecord.setTotalPartsInRecord(totalPartsInRecord);
				
				long[] numberOfRooms = getNumberOfRooms(text); 
				avgRoomsRecord.setNumberOfRooms(numberOfRooms);
				
				context.write(new Text(state), avgRoomsRecord);
				
			}
		}
		
	}
	
	private long[] getNumberOfRooms(String text) {
		long[] numberOfRooms = new long[AverageRoomsRecord.TOTAL_ROOMS];
		
		int count = 0; 
		for (int i = 2388; i < 2461; i+=9) {
			Long roomNumber = Long.parseLong(text.substring(i, i + 9));
			numberOfRooms[count] = roomNumber; 
			count++; 
		}
		
		
		return numberOfRooms; 
		
	}
}
