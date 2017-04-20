package cs455.hadoop.combiners;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import cs455.hadoop.records.ElderlyPercentageRecord;

public class ElderlyPercentageCombiner extends Reducer<Text, ElderlyPercentageRecord, Text, ElderlyPercentageRecord> {

	
	@Override
	protected void reduce(Text key, Iterable<ElderlyPercentageRecord> values, Context context) throws IOException, InterruptedException {
		double totalPopulation = 0.0; 
		double elderlyPopulation = 0.0; 
		
		for (ElderlyPercentageRecord record : values) {
			elderlyPopulation += record.getElderlyPopulation(); 
			totalPopulation += record.getTotalPopulation(); 
			
		}
		
		ElderlyPercentageRecord elderlyRecord = new ElderlyPercentageRecord(); 
		elderlyRecord.setElderlyPopulation(elderlyPopulation);
		elderlyRecord.setTotalPopulation(totalPopulation);
		
		context.write(key, elderlyRecord);
	}
}
