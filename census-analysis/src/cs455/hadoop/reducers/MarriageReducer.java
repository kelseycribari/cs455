package cs455.hadoop.reducers;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import cs455.hadoop.records.MarriageRecord;

public class MarriageReducer extends Reducer<Text, MarriageRecord, Text, MarriageRecord> {

	@Override 
	protected void reduce(Text key, Iterable<MarriageRecord> values, Context context) {
		long logicalRecordPartNumber = 0; 
		long totalPartsInRecord = 0; 
		long unmarriedMales = 0; 
		long unmarriedFemales = 0; 
		long totalPopulation = 0; 
		
		for (MarriageRecord record : values) {
			logicalRecordPartNumber = record.getLogicalRecordPartNumber();
			totalPartsInRecord = record.getTotalPartsInRecord();
			unmarriedMales = unmarriedMales + record.getUnmarriedMales();
			unmarriedFemales = unmarriedFemales + record.getUnmarriedFemales();
			totalPopulation = totalPopulation + record.getPopulation();
			
		}
		
		MarriageRecord marriageRecord = new MarriageRecord(); 
		marriageRecord.setUnmarriedFemales(unmarriedFemales);
		marriageRecord.setUnmarriedMales(unmarriedMales);
		marriageRecord.setPopulation(totalPopulation);
		marriageRecord.setLogicalRecordPartNumber(logicalRecordPartNumber);
		marriageRecord.setTotalPartsInRecord(totalPartsInRecord);
		
		try {
			context.write(key, marriageRecord);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
