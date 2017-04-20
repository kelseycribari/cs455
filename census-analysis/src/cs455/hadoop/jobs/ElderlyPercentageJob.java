package cs455.hadoop.jobs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import cs455.hadoop.combiners.ElderlyPercentageCombiner;
import cs455.hadoop.mappers.ElderlyPercentageMapper;
import cs455.hadoop.records.ElderlyPercentageRecord;
import cs455.hadoop.reducers.ElderlyPercentageReducer;




public class ElderlyPercentageJob {

	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration(); 
		Job job = Job.getInstance(conf, "Elderly Percentage");
		
		job.setJarByClass(ElderlyPercentageJob.class);
		job.setMapperClass(ElderlyPercentageMapper.class);
		job.setCombinerClass(ElderlyPercentageCombiner.class);
		job.setNumReduceTasks(1);
		job.setReducerClass(ElderlyPercentageReducer.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(ElderlyPercentageRecord.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(ElderlyPercentageRecord.class);
		
		Path inputPath = new Path(args[0]);
		Path outputPath = new Path(args[1]);
		
		FileInputFormat.addInputPath(job, inputPath);
		FileOutputFormat.setOutputPath(job, outputPath);
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
