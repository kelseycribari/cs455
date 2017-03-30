package cs455.hadoop.records;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable; 

/*
 * Author: Kelsey Cribari 
 * Date: 3-30-17
 * 
 * Contains Information about the Tenure section (Renting residences vs. owning residences)
 */

public class TenureRecord implements Writable {

	private long residencesRented = 0; 
	private long residencesOwned = 0; 
	
	private long logicalPartNumber = 0; 
	private long totalPartsInRecord = 0; 
	
	
	public long getResidencesRented() {
		return this.residencesRented; 
	}
	public long getResidencesOwned() {
		return this.residencesOwned; 
	}
	public void setResidencesRented(long rented) {
		this.residencesRented = rented; 
	}
	public void setResidencesOwned(long owned) {
		this.residencesOwned = owned; 
	}
	public double getRentedPercentage() {
		double total = getResidencesRented() + getResidencesOwned(); 
		double rentedPercentage = (getResidencesRented() / total) * 100; 
		
		return rentedPercentage; 
	}
	public double getOwnedPercentage() {
		double total = getResidencesRented() + getResidencesOwned(); 
		double ownedPercentage = (getResidencesOwned() / total) * 100; 
		
		return ownedPercentage; 
	}
	
	public long getLogicalPartNumber() {
		return this.logicalPartNumber;
	}
	public long getTotalPartsInRecord() {
		return this.totalPartsInRecord; 
	}
	public void setLogicalPartNumber(long num) {
		this.logicalPartNumber = num; 
	}
	public void setTotalPartsInRecord(long num) {
		this.totalPartsInRecord = num; 
	}
	
	public void readFields(DataInput dataInput) throws IOException {
		residencesRented = dataInput.readLong(); 
		residencesOwned = dataInput.readLong(); 
		
	}
	
	public void write(DataOutput dataOutput) throws IOException {
		//write number rented
		dataOutput.writeLong(residencesRented);
		//write number owned
		dataOutput.writeLong(residencesOwned); 
	}
	
	@Override 
	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		
		sb.append("Tenure Record:");
		sb.append("\n");
		sb.append("Residences rented: " + getResidencesRented()); 
		sb.append("\n");
		sb.append("Residences owned: " + getResidencesOwned());
		sb.append("\n");
		sb.append("Rented percentage: " + getRentedPercentage());
		sb.append("\n");
		sb.append("Owned percentage:" + getOwnedPercentage());
		sb.append("\n");
		
		return sb.toString(); 
		
	}
	
}
