package cs455.hadoop.reducers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import cs455.hadoop.records.AverageRoomsRecord;

public class AverageRoomsReducer extends Reducer<Text, AverageRoomsRecord, Text, Text>{

	private long percentile; 
	
	@Override
	protected void reduce(Text key, Iterable<AverageRoomsRecord> values, Context context) {
		List<Long> roomAverages = new ArrayList<Long>(); 
		
		for (AverageRoomsRecord record : values) {
			long average = getAverage(record.getNumberOfRooms());
			roomAverages.add(average); 
		}
		
		percentile = get95thPercentile(roomAverages); 
		
	}
	
	@Override 
	protected void cleanup(Context context) throws IOException, InterruptedException {
		Text text = new Text("95th Percentile Among States: ");
		Text secondText = new Text(percentile + " rooms");
		context.write(text, secondText);
	}
	
	
	private long getAverage(long[] numberOfRooms) {
		long totalHouses = 0; 
		long totalRooms = 0; 
		long average = 0; 
		for (int i = 0; i < numberOfRooms.length; i++) {
			long housesWithCurrentRooms = numberOfRooms[i];
			totalHouses += housesWithCurrentRooms; 
			for (int j = 0; j < housesWithCurrentRooms; j++) {
				totalRooms = totalRooms + i + 1; 
			}
		}
		average = totalRooms / totalHouses; 
		
		
		return average;  
	}
	
	private long get95thPercentile(List<Long> roomAverages) {
		Collections.sort(roomAverages);
		
		double percentile = roomAverages.size() * 0.95 - 1; 
		int percentileIndex = (int) percentile; 
		return roomAverages.get(percentileIndex); 
	}
}
