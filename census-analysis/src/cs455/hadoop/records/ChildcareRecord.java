package cs455.hadoop.records;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class ChildcareRecord implements Writable {
	
	
	private long logicalRecordPartNumber = 0; 
	private long totalPartsInRecord = 0; 
	
	private long totalPopulation = 0; 
	private long population13AndUnder = 0; 
	
	private double percent13AndUnder = 0.0; 
	
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
	
	public long getTotalPopulation() {
		return this.totalPopulation;
	}
	public void setTotalPopulation(long population) {
		this.totalPopulation = population; 
	}
	public long getPopulation13AndUnder() {
		return this.population13AndUnder;
	}
	public void setPopulation13AndUnder(long population) {
		this.population13AndUnder = population; 
	}

	@Override
	public void readFields(DataInput dataInput) throws IOException {
		population13AndUnder = dataInput.readLong(); 
		totalPopulation = dataInput.readLong(); 
		
	}

	@Override
	public void write(DataOutput dataOutput) throws IOException {
		dataOutput.writeLong(population13AndUnder); 
		dataOutput.writeLong(totalPopulation);
		
	}

}
