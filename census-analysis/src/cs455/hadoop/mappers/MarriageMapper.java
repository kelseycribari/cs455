package cs455.hadoop.mappers;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import cs455.hadoop.records.MarriageRecord;
import cs455.hadoop.util.ParsingUtil;

public class MarriageMapper extends Mapper<LongWritable, Text, Text, MarriageRecord> {
	
	@Override
	protected void map(LongWritable keyIn, Text valueIn, Context context) {
		
		MarriageRecord marriageRecord = new MarriageRecord(); 
		String text = valueIn.toString(); 
		String summaryLevel = ParsingUtil.summaryLevel(text);
		
		if (summaryLevel == "100") {
			String state = ParsingUtil.state(text);
			Long logicalRecordPartNumber = ParsingUtil.logicalRecordPartNumber(text);
			Long totalPartsInRecord = ParsingUtil.totalPartsInRecord(text);
			
			//Because records are located in segment 1?
			if (!logicalRecordPartNumber.equals(totalPartsInRecord)) {
				marriageRecord.setLogicalRecordPartNumber(logicalRecordPartNumber);
				marriageRecord.setTotalPartsInRecord(totalPartsInRecord);
				
				Long malesUnmarried = Long.parseLong(text.substring(4422, 4431));
				Long femalesUnmarried = Long.parseLong(text.substring(4467, 4476));
				Long population = Long.parseLong(text.substring(300, 309));
				
				marriageRecord.setUnmarriedMales(malesUnmarried);
				marriageRecord.setUnmarriedFemales(femalesUnmarried);
				marriageRecord.setPopulation(population);
				
				try {
					context.write(new Text(state), marriageRecord);
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
