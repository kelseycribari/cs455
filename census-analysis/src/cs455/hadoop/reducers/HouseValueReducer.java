package cs455.hadoop.reducers;

import java.io.IOException;
import java.util.Map;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import cs455.hadoop.records.HouseValueRecord;

public class HouseValueReducer extends Reducer<Text, HouseValueRecord, Text, HouseValueRecord> {

	
	@Override 
	protected void reduce(Text key, Iterable<HouseValueRecord> values, Context context) throws IOException, InterruptedException {
		long logicalRecordPartNumber = 0;
		long totalPartsInRecord = 0; 
		HouseValueRecord houseValueRecord = new HouseValueRecord(); 
		
		for (HouseValueRecord record : values) {
			logicalRecordPartNumber = record.getLogicalRecordPartNumber();
			totalPartsInRecord = record.getTotalPartsInRecord();
			addToCompleteMap(record.getValuesAndAmounts(), houseValueRecord);
			
		}
		
		houseValueRecord.setLogicalRecordPartNumber(logicalRecordPartNumber);
		houseValueRecord.setTotalPartsInRecord(totalPartsInRecord);
		
		
		context.write(key, houseValueRecord);
	}
	
	
	private void addToCompleteMap(Map<String, Long> current, HouseValueRecord houseValueRecord) {
		Map<String, Long> completeMap = houseValueRecord.getValuesAndAmounts(); 
		long value = 0; 
		for (Map.Entry<String, Long> entry : current.entrySet()) {
			value = completeMap.get(entry.getKey());
			value += entry.getValue(); 
			completeMap.put(entry.getKey(), value);
		}
		
		houseValueRecord.setValuesAndAmounts(completeMap);
	}
}
