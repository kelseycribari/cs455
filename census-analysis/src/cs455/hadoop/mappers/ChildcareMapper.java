package cs455.hadoop.mappers;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import cs455.hadoop.records.ChildcareRecord;
import cs455.hadoop.util.ParsingUtil;

public class ChildcareMapper extends Mapper<LongWritable, Text, Text, ChildcareRecord> {

	
	@Override 
	protected void map(LongWritable keyIn, Text valueIn, Context context) throws IOException, InterruptedException {
		
		String text = valueIn.toString(); 
		String summaryLevel = ParsingUtil.summaryLevel(text);
		
		if (summaryLevel.equals("100")) {
			String state = ParsingUtil.state(text);
			Long logicalRecordPartNumber = ParsingUtil.logicalRecordPartNumber(text);
			Long totalPartsInRecord = ParsingUtil.totalPartsInRecord(text);
			
			ChildcareRecord childcareRecord = new ChildcareRecord(); 
			
			if (!logicalRecordPartNumber.equals(totalPartsInRecord)) {
				childcareRecord.setLogicalRecordPartNumber(logicalRecordPartNumber);
				childcareRecord.setTotalPartsInRecord(totalPartsInRecord);
				
				Long population13AndUnder = getPopulation13AndUnder(text);
				childcareRecord.setPopulation13AndUnder(population13AndUnder);
				
				Long totalPopulation = getTotalPopulation(text); 
				childcareRecord.setTotalPopulation(totalPopulation);
				
				context.write(new Text(state), childcareRecord);
			}
		}
	}
	
	private Long getPopulation13AndUnder(String text) {
		Long population13AndUnder = 0l; 
		Long temp = 0l; 
		for (int i = 795; i < 859; i+=9) {
			temp = Long.parseLong(text.substring(i, i + 9));
			population13AndUnder += temp; 
		}
		
		return population13AndUnder; 
	}
	
	private Long getTotalPopulation(String text) {
		return Long.parseLong(text.substring(300, 309));
	}
}
