package cs455.hadoop.records;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.io.Writable;

public class RentMedianRecord implements Writable {
	
	private long logicalRecordPartNumber = 0; 
	private long totalPartsInRecord = 0; 
	
	public static final List<String> RENT_VALUES = Arrays.asList("Less than $100",
													"$100 to $149", 
													"$150 to $199",
													"$200 to $249", 
													"$250 to $299", 
													"$300 to $349", 
													"$350 to $399",
													"$400 to $449", 
													"$450 to $499",
													"$500 to $549",
													"$550 to $599",
													"$600 to $649", 
													"$650 to $699", 
													"$700 to $749", 
													"$750 to $999", 
													"$1000 or more");
	
	private Map<String, Long> map; 
	
	public RentMedianRecord() {
		map = new LinkedHashMap<String, Long>(); 
		for (String key : RENT_VALUES) {
			map.put(key, 0l);
		}
	}
	
	public long getLogicalRecordPartNumber() {
		return logicalRecordPartNumber; 
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
	public Map<String, Long> getMap() {
		return this.map; 
	}
	public void setMap(Map<String, Long> map) {
		this.map = map; 
	}

	@Override
	public void readFields(DataInput dataInput) throws IOException {
		for (String key : map.keySet()) {
			map.put(key, dataInput.readLong());
		}
		
	}

	@Override
	public void write(DataOutput dataOutput) throws IOException {
		for (Long l : map.values()) {
			dataOutput.writeLong(l);
		}
	}
	
	private String computeMedian() {
		ArrayList<Long> numbers = new ArrayList<Long>(map.values());
		Long total = 0l; 
		for (Long l : numbers) {
			total += l; 
		}
		
		Long median = total / 2; 
		Long temp = 0l; 
		for (Map.Entry<String, Long> entry : map.entrySet()) {
			temp += entry.getValue(); 
			if (temp >= median) {
				return entry.getKey(); 
			}
		}
		
		return RENT_VALUES.get(RENT_VALUES.size() - 1);
	}
	
	@Override 
	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		String medianRange = computeMedian(); 
		sb.append("Rent ranges and values (for testing):" + "\n"); 
		
		Set s = map.entrySet(); 
		Iterator i = s.iterator(); 
		
		while(i.hasNext()) {
			Map.Entry e = (Map.Entry)i.next(); 
			sb.append(e.getKey() + ": ");
			sb.append(e.getValue() + "\n");
		}
		
		sb.append("\n");
		sb.append("Median rent range: ");
		sb.append(medianRange + "\n");
		
		
		
		return sb.toString(); 
	}

}
