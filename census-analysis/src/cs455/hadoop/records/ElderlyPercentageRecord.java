package cs455.hadoop.records;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class ElderlyPercentageRecord implements Writable {
	
	
	private long logicalRecordPartNumber = 0; 
	private long totalPartsInRecord = 0; 
	
	private double elderlyPopulation = 0.0; 
	private double totalPopulation = 0.0; 
	
	public ElderlyPercentageRecord() {
		
	}
	
	public long getLogicalRecordPartNumber() {
		return this.logicalRecordPartNumber;
	}
	public void setLogicalRecordPartNumber(long num) {
		this.logicalRecordPartNumber = num; 
	}
	public long getTotalPartsInRecord() {
		return this.totalPartsInRecord;
	}
	public void setTotalPartsInRecord(long num) {
		this.totalPartsInRecord = num; 
	}
	
	public double getElderlyPopulation() {
		return this.elderlyPopulation;
	}
	public void setElderlyPopulation(double elderly) {
		this.elderlyPopulation = elderly;
	}
	public double getTotalPopulation() {
		return this.totalPopulation;
	}
	public void setTotalPopulation(double total) {
		this.totalPopulation = total; 
	}

	@Override
	public void readFields(DataInput dataInput) throws IOException {
		elderlyPopulation = dataInput.readDouble(); 
		totalPopulation = dataInput.readDouble(); 
		
	}

	@Override
	public void write(DataOutput dataOutput) throws IOException {
		dataOutput.writeDouble(elderlyPopulation);
		dataOutput.writeDouble(totalPopulation);
		
	}

}
