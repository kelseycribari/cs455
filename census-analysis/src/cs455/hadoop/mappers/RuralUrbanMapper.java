package cs455.hadoop.mappers;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import cs455.hadoop.records.RuralUrbanRecord;
import cs455.hadoop.util.ParsingUtil;

public class RuralUrbanMapper extends Mapper<LongWritable, Text, Text, RuralUrbanRecord>{
	
	
	@Override 
	protected void map(LongWritable keyIn, Text valueIn, Context context) throws IOException, InterruptedException {
		RuralUrbanRecord ruralUrbanRecord = new RuralUrbanRecord(); 
		String text = valueIn.toString(); 
		String summaryLevel = ParsingUtil.summaryLevel(text); 
		
		if (summaryLevel.equals("100")) {
			String state = ParsingUtil.state(text);
			Long logicalRecordPartNumber = ParsingUtil.logicalRecordPartNumber(text);
			Long totalPartsInRecord = ParsingUtil.totalPartsInRecord(text);
			
			if (logicalRecordPartNumber.equals(totalPartsInRecord)) {
				ruralUrbanRecord.setLogicalRegordRecordPartNumber(logicalRecordPartNumber);
				ruralUrbanRecord.setTotalPartsInRecord(totalPartsInRecord);
				
				Long urban = getUrban(text); 
				ruralUrbanRecord.setUrbanHouseholds(urban);
				
				Long rural = getRural(text); 
				ruralUrbanRecord.setRuralHouseholds(rural); 
				
				Long undefined = getUndefined(text); 
				ruralUrbanRecord.setUndefinedHouseholds(undefined);
				
				context.write(new Text(state), ruralUrbanRecord);
				
				
				
			}
		}
	}
	
	
	private Long getRural(String text) {
		Long rural = Long.parseLong(text.substring(1839, 1848));
		
		return rural; 
	}
	
	private Long getUrban(String text) {
		Long urban = 0l;
		
		urban = Long.parseLong(text.substring(1821, 1830));
		urban += Long.parseLong(text.substring(1830, 1839));
		
		return urban; 
	}
	
	private Long getUndefined(String text) {
		Long undefined = Long.parseLong(text.substring(1848, 1857));
		return undefined; 
	}

}
