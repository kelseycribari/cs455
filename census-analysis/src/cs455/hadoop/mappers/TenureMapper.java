package cs455.hadoop.mappers;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import cs455.hadoop.records.TenureRecord;
import cs455.hadoop.util.ParsingUtil;

public class TenureMapper extends Mapper<LongWritable, Text, Text, TenureRecord>{
	
	/*
	 * c(non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Mapper#map(KEYIN, VALUEIN, org.apache.hadoop.mapreduce.Mapper.Context)
	 */
	@Override
	protected void map(LongWritable keyIn, Text valueIn, Context context) {
		
		TenureRecord tenureRecord = new TenureRecord(); 
		//Gets unparsed text of the value passed in
		String text = valueIn.toString(); 
		String summaryLevel = ParsingUtil.summaryLevel(text);
		
		//Make sure the summary level is 100 before proceeding
		if (summaryLevel == "100") {
			String state = ParsingUtil.state(text);
			Long logicalRecordPartNumber = ParsingUtil.logicalRecordPartNumber(text);
			Long totalPartsInRecord = ParsingUtil.totalPartsInRecord(text);
			
			if (logicalRecordPartNumber == totalPartsInRecord) {
				tenureRecord.setLogicalPartNumber(logicalRecordPartNumber);
				tenureRecord.setTotalPartsInRecord(totalPartsInRecord);
				
				Long owned = Long.parseLong(text.substring(1803, 1812));
				Long rented = Long.parseLong(text.substring(1812, 1821));
				
				tenureRecord.setResidencesOwned(owned);
				tenureRecord.setResidencesRented(rented);
				
				try {
					context.write(new Text(state), tenureRecord);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

}
