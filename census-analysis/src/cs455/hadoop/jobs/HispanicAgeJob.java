package cs455.hadoop.jobs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import cs455.hadoop.mappers.HispanicAgeMapper;
import cs455.hadoop.records.HispanicAgeRecord;
import cs455.hadoop.reducers.HispanicAgeReducer;


public class HispanicAgeJob {
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		Configuration conf = new Configuration(); 
		Job job = Job.getInstance(conf, "Hispanic Age");
		
		job.setJarByClass(HispanicAgeJob.class);
		job.setMapperClass(HispanicAgeMapper.class);
		job.setCombinerClass(HispanicAgeReducer.class);
		job.setReducerClass(HispanicAgeReducer.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(HispanicAgeRecord.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(HispanicAgeRecord.class);
		
		Path inputPath = new Path(args[0]);
		Path outputPath = new Path(args[1]);
		
		FileInputFormat.addInputPath(job, inputPath);
		FileOutputFormat.setOutputPath(job, outputPath);
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
