package cs455.hadoop.records;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class AverageRoomsRecord implements Writable {

	private long logicalRecordPartNumber = 0; 
	private long totalPartsInRecord = 0; 
	
	public static final int TOTAL_ROOMS = 9; 
	private long[] numberOfRooms; 
	
	public AverageRoomsRecord() {
		numberOfRooms = new long[TOTAL_ROOMS];
	}
	public long[] getNumberOfRooms() {
		return numberOfRooms; 
	}
	public void setNumberOfRooms(long[] numberOfRooms) {
		this.numberOfRooms = numberOfRooms; 
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
	
	@Override
	public void readFields(DataInput dataInput) throws IOException {
		for (int i = 0; i < TOTAL_ROOMS; i++) {
			numberOfRooms[i] = dataInput.readLong(); 
		}
		
	}

	@Override
	public void write(DataOutput dataOutput) throws IOException {
		for (int i = 0; i < TOTAL_ROOMS; i++) {
			dataOutput.writeLong(numberOfRooms[i]);
		}
		
	}

}
