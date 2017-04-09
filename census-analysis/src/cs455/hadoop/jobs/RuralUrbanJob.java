package cs455.hadoop.jobs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import cs455.hadoop.mappers.RuralUrbanMapper;
import cs455.hadoop.records.RuralUrbanRecord;
import cs455.hadoop.reducers.RuralUrbanReducer;

public class RuralUrbanJob {

	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		Configuration conf = new Configuration(); 
		Job job = Job.getInstance(conf, "Rural Urban");
		
		job.setJarByClass(RuralUrbanJob.class);
		job.setMapperClass(RuralUrbanMapper.class);
		job.setCombinerClass(RuralUrbanReducer.class);
		job.setReducerClass(RuralUrbanReducer.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(RuralUrbanRecord.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(RuralUrbanRecord.class);
		
		Path inputPath = new Path(args[0]);
		Path outputPath = new Path(args[1]);
		
		FileInputFormat.addInputPath(job, inputPath);
		FileOutputFormat.setOutputPath(job, outputPath);
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
