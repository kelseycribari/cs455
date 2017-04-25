package cs455.hadoop.combiners;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import cs455.hadoop.records.ChildcareRecord;

public class ChildcareCombiner extends Reducer<Text, ChildcareRecord, Text, ChildcareRecord>{

	
	@Override
	protected void reduce(Text key, Iterable<ChildcareRecord> values, Context context) throws IOException, InterruptedException {
		double population13AndUnder = 0l; 
		double totalPopulation = 0l; 
		
		
		for (ChildcareRecord record : values) {
			population13AndUnder += record.getPopulation13AndUnder(); 
			totalPopulation += record.getTotalPopulation();
		}
		
		ChildcareRecord childcareRecord = new ChildcareRecord(); 
		childcareRecord.setPopulation13AndUnder(population13AndUnder);
		childcareRecord.setTotalPopulation(totalPopulation);
		
		context.write(key, childcareRecord);
	}
}
