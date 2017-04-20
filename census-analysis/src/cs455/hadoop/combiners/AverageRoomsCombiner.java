package cs455.hadoop.combiners;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import cs455.hadoop.records.AverageRoomsRecord;

public class AverageRoomsCombiner extends Reducer<Text, AverageRoomsRecord, Text, AverageRoomsRecord>{

	
	@Override
	protected void reduce(Text key, Iterable<AverageRoomsRecord> values, Context context) throws IOException, InterruptedException {
		AverageRoomsRecord avgRoomsRecord = new AverageRoomsRecord(); 
		
		
		for (AverageRoomsRecord record : values) {
			long[] currentNumberRooms = record.getNumberOfRooms(); 
			combineNumberOfRooms(currentNumberRooms, avgRoomsRecord);
			
		}
		
		context.write(key, avgRoomsRecord);
	}
	
	private void combineNumberOfRooms(long[] currentNumberRooms, AverageRoomsRecord avgRoomsRecord) {
		long[] totalNumberRooms = avgRoomsRecord.getNumberOfRooms();
		for (int i = 0; i < AverageRoomsRecord.TOTAL_ROOMS; i++) {
			totalNumberRooms[i] += currentNumberRooms[i];
		}
		
		avgRoomsRecord.setNumberOfRooms(totalNumberRooms);
	}
}
