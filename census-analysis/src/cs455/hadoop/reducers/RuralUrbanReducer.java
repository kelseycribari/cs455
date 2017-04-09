package cs455.hadoop.reducers;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import cs455.hadoop.records.RuralUrbanRecord;

public class RuralUrbanReducer extends Reducer<Text, RuralUrbanRecord, Text, RuralUrbanRecord>{

	
	@Override
	protected void reduce(Text key, Iterable<RuralUrbanRecord> values, Context context) throws IOException, InterruptedException {
		
		long logicalRecordPartNumber = 0; 
		long totalPartsInRecord = 0; 
		
		long urbanHouseholds = 0; 
		long ruralHouseholds = 0; 
		long undefinedHouseholds = 0; 
		
		for (RuralUrbanRecord record : values) {
			logicalRecordPartNumber = record.getLogicalRecordPartNumber();
			totalPartsInRecord = record.getTotalPartsInRecord(); 
			urbanHouseholds = urbanHouseholds + record.getUrbanHouseholds();
			ruralHouseholds = ruralHouseholds + record.getRuralHouseholds(); 
			undefinedHouseholds = undefinedHouseholds + record.getUndefinedHouseholds(); 
		}
		
		RuralUrbanRecord ruralUrbanRecord = new RuralUrbanRecord(); 
		ruralUrbanRecord.setLogicalRegordRecordPartNumber(logicalRecordPartNumber);
		ruralUrbanRecord.setTotalPartsInRecord(totalPartsInRecord);
		ruralUrbanRecord.setUrbanHouseholds(urbanHouseholds);
		ruralUrbanRecord.setRuralHouseholds(ruralHouseholds);
		ruralUrbanRecord.setUndefinedHouseholds(undefinedHouseholds);
		
		
		context.write(key, ruralUrbanRecord);
	}
}
