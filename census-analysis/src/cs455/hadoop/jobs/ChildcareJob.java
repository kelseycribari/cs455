package cs455.hadoop.jobs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import cs455.hadoop.combiners.ChildcareCombiner;
import cs455.hadoop.mappers.ChildcareMapper;
import cs455.hadoop.records.ChildcareRecord;
import cs455.hadoop.reducers.ChildcareReducer;



public class ChildcareJob {
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration(); 
		Job job = Job.getInstance(conf, "Childcare Percentage");
		
		job.setJarByClass(ChildcareJob.class);
		job.setMapperClass(ChildcareMapper.class);
		job.setCombinerClass(ChildcareCombiner.class);
		job.setNumReduceTasks(1);
		job.setReducerClass(ChildcareReducer.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(ChildcareRecord.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(ChildcareRecord.class);
		
		Path inputPath = new Path(args[0]);
		Path outputPath = new Path(args[1]);
		
		FileInputFormat.addInputPath(job, inputPath);
		FileOutputFormat.setOutputPath(job, outputPath);
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
