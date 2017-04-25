package cs455.hadoop.reducers;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import cs455.hadoop.records.ChildcareRecord;

public class ChildcareReducer extends Reducer<Text, ChildcareRecord, Text, DoubleWritable> {

	private double percent13AndUnder = 0.0; 
	private String state = ""; 
	
	@Override 
	protected void reduce(Text key, Iterable<ChildcareRecord> values, Context context) {
		
		//double currentPercent = 0.0; 
		
		for (ChildcareRecord record : values) {
			double currentPercent = (record.getPopulation13AndUnder() / record.getTotalPopulation()) * 100; 
			if (currentPercent > percent13AndUnder) {
				percent13AndUnder = currentPercent; 
				state = key.toString(); 
			}
		}
	}
	
	//cleanup method so that it writes out the state with the highest percent at the very end instead of after each reduce iteration 
	@Override 
	protected void cleanup(Context context) throws IOException, InterruptedException {
		Text finalString = new Text("State with highest percentage of children 13 and under: " + state + "\nPercentage: "); 
		context.write(finalString, new DoubleWritable(percent13AndUnder));
	}
}
