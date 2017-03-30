package cs455.hadoop.jobs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import cs455.hadoop.mappers.TenureMapper;
import cs455.hadoop.records.TenureRecord;
import cs455.hadoop.reducers.TenureReducer;

public class TenureJob {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration(); 

		Job job = Job.getInstance(conf, "Tenure");

		job.setJarByClass(TenureJob.class);
		job.setMapperClass(TenureMapper.class);
		job.setCombinerClass(TenureReducer.class);
		job.setReducerClass(TenureReducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(TenureRecord.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(TenureRecord.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		

	}

}
