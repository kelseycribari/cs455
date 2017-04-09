package cs455.hadoop.reducers;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import cs455.hadoop.records.HispanicAgeRecord;

public class HispanicAgeReducer extends Reducer<Text, HispanicAgeRecord, Text, HispanicAgeRecord> {

	
	@Override
	protected void reduce(Text key, Iterable<HispanicAgeRecord> values, Context context) throws IOException, InterruptedException {
		
		long logicalRecordPartNumber = 0; 
		long totalPartsInRecord = 0; 
		long totalFemaleHispanicPopulation = 0; 
		long totalMaleHispanicPopulation = 0; 
		long females18AndBelow = 0; 
		long males18AndBelow = 0; 
		long females19To29 = 0; 
		long males19To29 = 0; 
		long females30To39 = 0; 
		long males30To39 = 0; 
		
		for (HispanicAgeRecord record : values) {
			logicalRecordPartNumber = record.getLogicalRecordPartNumber();
			totalPartsInRecord = record.getTotalPartsInRecord();
			females18AndBelow = females18AndBelow + record.getFemales18AndBelow();
			males18AndBelow = males18AndBelow + record.getMales18AndBelow();
			females19To29 = females19To29 + record.getFemalesBetween19And29();
			males19To29 = males19To29 + record.getMalesBetween19And29(); 
			females30To39 = females30To39 + record.getFemalesBetween30And39();
			males30To39 = males30To39 + record.getMalesBetween30And39();
			totalFemaleHispanicPopulation = totalFemaleHispanicPopulation + record.getTotalFemaleHispanicPopulation();
			totalMaleHispanicPopulation = totalMaleHispanicPopulation + record.getMaleHispanicPopulation(); 
		}
		
		HispanicAgeRecord hispanicRecord = new HispanicAgeRecord(); 
		hispanicRecord.setLogicalRecordPartNumber(logicalRecordPartNumber);
		hispanicRecord.setTotalPartsInRecord(totalPartsInRecord);
		hispanicRecord.setFemales18AndBelow(females18AndBelow);
		hispanicRecord.setMales18AndBelow(males18AndBelow);
		hispanicRecord.setFemalesBetween19And29(females19To29);
		hispanicRecord.setMalesBetween19And29(males19To29);
		hispanicRecord.setFemalesBetween30And39(females30To39);
		hispanicRecord.setMalesBetween30And39(males30To39);
		hispanicRecord.setFemaleHispanicPopulation(totalFemaleHispanicPopulation);
		hispanicRecord.setMaleHispanicPopulation(totalMaleHispanicPopulation);
		
		context.write(key, hispanicRecord);
		
	}
}
