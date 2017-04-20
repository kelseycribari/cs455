package cs455.hadoop.jobs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import cs455.hadoop.combiners.AverageRoomsCombiner;
import cs455.hadoop.mappers.AverageRoomsMapper;
import cs455.hadoop.records.AverageRoomsRecord;
import cs455.hadoop.reducers.AverageRoomsReducer;


public class AverageRoomsJob {

	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration(); 
		Job job = Job.getInstance(conf, "Average Rooms Percentile");
		
		job.setJarByClass(AverageRoomsJob.class);
		job.setMapperClass(AverageRoomsMapper.class);
		job.setCombinerClass(AverageRoomsCombiner.class);
		job.setNumReduceTasks(1);
		job.setReducerClass(AverageRoomsReducer.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(AverageRoomsRecord.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(AverageRoomsRecord.class);
		
		Path inputPath = new Path(args[0]);
		Path outputPath = new Path(args[1]);
		
		FileInputFormat.addInputPath(job, inputPath);
		FileOutputFormat.setOutputPath(job, outputPath);
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
