package cs455.hadoop.mappers;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import cs455.hadoop.records.RentMedianRecord;
import cs455.hadoop.util.ParsingUtil;

public class RentMedianMapper extends Mapper<LongWritable, Text, Text, RentMedianRecord> {

	
	@Override
	protected void map(LongWritable keyIn, Text valueIn, Context context) throws IOException, InterruptedException {
		String text = valueIn.toString(); 
		String summaryLevel = ParsingUtil.summaryLevel(text); 
		
		if (summaryLevel.equals("100")) {
			String state = ParsingUtil.state(text); 
			Long logicalRecordPartNumber = ParsingUtil.logicalRecordPartNumber(text);
			Long totalPartsInRecord = ParsingUtil.totalPartsInRecord(text);
			
			
			if (logicalRecordPartNumber.equals(totalPartsInRecord)) {
				RentMedianRecord rentRecord = new RentMedianRecord(); 
				rentRecord.setLogicalRecordPartNumber(logicalRecordPartNumber);
				rentRecord.setTotalPartsInRecord(totalPartsInRecord);
				Map<String, Long> records = getAndStoreValues(text); 
				rentRecord.setMap(records);
				
				
				context.write(new Text(state), rentRecord);
				
			}
		}
	}
	
	
	private Map<String, Long> getAndStoreValues(String text) {
		Long temp = 0l; 
		Map<String, Long> numbers = new LinkedHashMap<String, Long>();  
		int counter = 0; 
		for (int i = 3450; i < 3586; i+=9) {
			temp = Long.parseLong(text.substring(i, i + 9));
			numbers.put(RentMedianRecord.RENT_VALUES.get(counter), temp); 
			counter++; 
		}
		
		return numbers;
	}
}
