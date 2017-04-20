package cs455.hadoop.reducers;

import java.io.IOException;
import java.util.Map;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import cs455.hadoop.records.HouseValueRecord;
import cs455.hadoop.records.RentMedianRecord;

public class RentMedianReducer extends Reducer<Text, RentMedianRecord, Text, RentMedianRecord>{

	
	@Override 
	protected void reduce(Text key, Iterable<RentMedianRecord> values, Context context) throws IOException, InterruptedException {
		long logicalRecordPartNumber = 0;
		long totalPartsInRecord = 0; 
		RentMedianRecord rentRecord = new RentMedianRecord(); 
		
		for (RentMedianRecord record : values) {
			logicalRecordPartNumber = record.getLogicalRecordPartNumber();
			totalPartsInRecord = record.getTotalPartsInRecord();
			addToCompleteMap(record.getMap(), rentRecord);
			
		}
		
		rentRecord.setLogicalRecordPartNumber(logicalRecordPartNumber);
		rentRecord.setTotalPartsInRecord(totalPartsInRecord);
		
		
		context.write(key, rentRecord);
	}
	
	
	private void addToCompleteMap(Map<String, Long> current, RentMedianRecord rentRecord) {
		Map<String, Long> completeMap = rentRecord.getMap(); 
		long value = 0; 
		for (Map.Entry<String, Long> entry : current.entrySet()) {
			value = completeMap.get(entry.getKey());
			value += entry.getValue(); 
			completeMap.put(entry.getKey(), value);
		}
		
		rentRecord.setMap(completeMap);
	}
}
