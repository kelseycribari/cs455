package cs455.hadoop.reducers;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import cs455.hadoop.records.ElderlyPercentageRecord;

public class ElderlyPercentageReducer extends Reducer<Text, ElderlyPercentageRecord, Text, DoubleWritable> {

	private double finalPercent = 0.0; 
	private String state = ""; 
	
	@Override
	protected void reduce(Text key, Iterable<ElderlyPercentageRecord> values, Context context) throws IOException, InterruptedException {
		
		
		for (ElderlyPercentageRecord record : values) {
			double currentPercent = (record.getElderlyPopulation() / record.getTotalPopulation()) * 100; 
			if (currentPercent > finalPercent) {
				finalPercent = currentPercent; 
				state = key.toString(); 
			}
		}
		
//		Text output = new Text("State with highest percentage: " + state + "\nPercentage: ");
//		context.write(output, new DoubleWritable(finalPercent));
	}
	
	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {
		Text output = new Text("State with highest percentage: " + state + "\nPercentage: ");
		context.write(output, new DoubleWritable(finalPercent));
	}
	
}
