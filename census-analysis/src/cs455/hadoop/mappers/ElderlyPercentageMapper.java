package cs455.hadoop.mappers;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import cs455.hadoop.records.ElderlyPercentageRecord;
import cs455.hadoop.util.ParsingUtil;

public class ElderlyPercentageMapper extends Mapper<LongWritable, Text, Text, ElderlyPercentageRecord> {

	
	@Override
	protected void map(LongWritable keyIn, Text valueIn, Context context) throws IOException, InterruptedException {
		String text = valueIn.toString(); 
		String summaryLevel = ParsingUtil.summaryLevel(text);
		
		if (summaryLevel.equals("100")) {
			String state = ParsingUtil.state(text); 
			Long logicalRecordPartNumber = ParsingUtil.logicalRecordPartNumber(text);
			Long totalPartsInRecord = ParsingUtil.totalPartsInRecord(text);
			
			ElderlyPercentageRecord elderlyRecord = new ElderlyPercentageRecord(); 
			
			if (!logicalRecordPartNumber.equals(totalPartsInRecord)) {
				elderlyRecord.setLogicalRecordPartNumber(logicalRecordPartNumber); 
				elderlyRecord.setTotalPartsInRecord(totalPartsInRecord);
				
				Double elderlyPopulation = getElderlyPopulation(text);
				elderlyRecord.setElderlyPopulation(elderlyPopulation);
				
				Double totalPopulation = getTotalPopulation(text); 
				elderlyRecord.setTotalPopulation(totalPopulation);
				
				
				context.write(new Text(state), elderlyRecord);
			}
		}
	}
	
	private Double getTotalPopulation(String text) {
		return Double.parseDouble(text.substring(300, 309));
	}
	private Double getElderlyPopulation(String text) {
		return Double.parseDouble(text.substring(1065, 1074));
	}
}
