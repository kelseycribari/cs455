package cs455.hadoop.records;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class RuralUrbanRecord implements Writable {
	
	private long logicalRecordPartNumber = 0; 
	private long totalPartsInRecord = 0; 
	
	private long urbanHouseholds = 0; 
	private long ruralHouseholds = 0; 
	private long undefinedHouseholds = 0; 
	private long totalHouseholds = 0; 
	
	
	public long getLogicalRecordPartNumber() {
		return this.logicalRecordPartNumber;
	}
	public void setLogicalRegordRecordPartNumber(long num) {
		this.logicalRecordPartNumber = num; 
	}
	public long getTotalPartsInRecord() {
		return this.totalPartsInRecord; 
	}
	public void setTotalPartsInRecord(long num) {
		this.totalPartsInRecord = num;
	}
	
	public long getUrbanHouseholds() {
		return this.urbanHouseholds;
	}
	public void setUrbanHouseholds(long num) {
		this.urbanHouseholds = num; 
	}
	public long getRuralHouseholds() {
		return this.ruralHouseholds;
	}
	public void setRuralHouseholds(long num) {
		this.ruralHouseholds = num; 
	}
	public long getUndefinedHouseholds() {
		return this.undefinedHouseholds;
	}
	public void setUndefinedHouseholds(long num) {
		this.undefinedHouseholds = num; 
	}
	public long getTotalHouseholds() {
		return this.totalHouseholds; 
	}
	public void setTotalHouseholds(long num) {
		this.totalHouseholds = num; 
	}
	
	private double getRuralPercentage() {
		double rural = getRuralHouseholds(); 
		double total = getRuralHouseholds() + getUrbanHouseholds() + getUndefinedHouseholds(); 
		double percentage = (rural / total) * 100.0; 
		
		return percentage; 
	}
	private double getUrbanPercentage() {
		double urban = getUrbanHouseholds(); 
		double total = getRuralHouseholds() + getUrbanHouseholds() + getUndefinedHouseholds(); 
		double percentage = (urban / total) * 100.0; 
		
		return percentage; 
	}
	private double getUndefinedPercentage() {
		double undefined = getUndefinedHouseholds(); 
		double total = getRuralHouseholds() + getUrbanHouseholds() + getUndefinedHouseholds(); 
		double percentage = (undefined / total) * 100.0; 
		
		return percentage; 
	}

	@Override
	public void readFields(DataInput dataInput) throws IOException {
		urbanHouseholds = dataInput.readLong(); 
		ruralHouseholds = dataInput.readLong(); 
		undefinedHouseholds = dataInput.readLong(); 
		
	}

	@Override
	public void write(DataOutput dataOutput) throws IOException {
		dataOutput.writeLong(urbanHouseholds);
		dataOutput.writeLong(ruralHouseholds);
		dataOutput.writeLong(undefinedHouseholds);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		double ruralPercentage = getRuralPercentage(); 
		double urbanPercentage = getUrbanPercentage(); 
		double undefinedPercentage = getUndefinedPercentage(); 
		
		sb.append("Total rural households: "); 
		sb.append(getRuralHouseholds() + "\n");
		sb.append("Total urban households: ");
		sb.append(getUrbanHouseholds() + "\n");
		sb.append("Total undefined households: "); 
		sb.append(getUndefinedHouseholds() + "\n");
		sb.append("Percentage of houses that are rural: "); 
		sb.append(ruralPercentage + "\n");
		sb.append("Percentage of houses that are urban: ");
		sb.append(urbanPercentage + "\n");
		sb.append("Percentage of houses undefined: ");
		sb.append(undefinedPercentage + "\n");
		sb.append("\n");
		
		return sb.toString(); 
		
	}

}
