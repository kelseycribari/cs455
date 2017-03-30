package cs455.hadoop.reducers;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


import cs455.hadoop.records.TenureRecord;

public class TenureReducer extends Reducer<Text, TenureRecord, Text, TenureRecord> {


	@Override
	protected void reduce(Text key, Iterable<TenureRecord> values, Context context) {
		long totalPartsInRecord = 0; 
		long logicalRecordPartNumber = 0; 
		long rented = 0l; 
		long owned = 0l; 
		
		for (TenureRecord record : values) {
			totalPartsInRecord = record.getTotalPartsInRecord(); 
			logicalRecordPartNumber = record.getLogicalPartNumber();
			rented = rented + record.getResidencesRented(); 
			owned = owned + record.getResidencesOwned(); 
			
		}
		
		TenureRecord tenureRecord = new TenureRecord(); 
		tenureRecord.setResidencesOwned(owned);
		tenureRecord.setResidencesRented(rented);
		
		tenureRecord.setLogicalPartNumber(logicalRecordPartNumber);
		tenureRecord.setTotalPartsInRecord(totalPartsInRecord);
		try {
			context.write(key, tenureRecord);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	

}
