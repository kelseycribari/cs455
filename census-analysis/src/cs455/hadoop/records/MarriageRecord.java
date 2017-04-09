package cs455.hadoop.records;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Writable;

public class MarriageRecord implements Writable {
	
	private long logicalRecordPartNumber = 0; 
	private long totalPartsInRecord = 0; 
	
	private long totalPopulation = 0; 
	private long unmarriedFemales = 0; 
	private long unmarriedMales = 0; 
	
	
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
	
	public long getPopulation() {
		return this.totalPopulation; 
	}
	public void setPopulation(long pop) {
		this.totalPopulation = pop; 
	}
	public long getUnmarriedFemales() {
		return this.unmarriedFemales; 
	}
	public void setUnmarriedFemales(long females) {
		this.unmarriedFemales = females; 
	}
	public long getUnmarriedMales() {
		return this.unmarriedMales; 
	}
	public void setUnmarriedMales(long males) {
		this.unmarriedMales = males; 
	}
	
	public double getMalesPercentage() { 
		return (getUnmarriedMales() / getPopulation()) * 100;  
	}
	public double getFemalesPercentage() {
		return (getUnmarriedFemales() / getPopulation()) * 100; 
	}
	
	

	@Override
	public void readFields(DataInput dataInput) throws IOException {
		unmarriedMales = dataInput.readLong(); 
		unmarriedFemales = dataInput.readLong(); 
		totalPopulation = dataInput.readLong(); 
		
	}

	@Override
	public void write(DataOutput dataOutput) throws IOException {
		dataOutput.writeLong(unmarriedMales); 
		dataOutput.writeLong(unmarriedFemales);
		dataOutput.writeLong(totalPopulation);
		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		double malePercentage = getMalesPercentage(); 
		double femalePercentage = getFemalesPercentage(); 
		
		sb.append("Males never married: "); 
		sb.append(getUnmarriedMales() + "\n");
		sb.append("Females never married: "); 
		sb.append(getUnmarriedFemales() + "\n");
		sb.append("Percentage of males never married: "); 
		sb.append(malePercentage + "\n");
		sb.append("Percentage of females never married: ");
		sb.append(femalePercentage + "\n");
		
		return sb.toString(); 
		
	}
	

}
