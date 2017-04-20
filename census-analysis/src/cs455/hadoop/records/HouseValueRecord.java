package cs455.hadoop.records;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.io.Writable;

public class HouseValueRecord implements Writable {
	
	private long logicalRecordPartNumber = 0; 
	private long totalPartsInRecord = 0; 
	
	public static final List<String> HOUSING_VALUES = Arrays.asList("Less than $15,000", 
											"$15,000 - $19,999", 
											"$20,000 - $24,999", 
											"$25,000 - $29,999", 
											"$30,000 - $34,999", 
											"$35,000 - $39,999", 
											"$40,000 - $44,999", 
											"$45,000 - $49,999",
											"$50,000 - $59,999", 
											"$60,000 - $74,999", 
											"$75,000 - $99,999", 
											"$100,000 - $124,999",
											"$125,000 - $149,999", 
											"$150,000 - $174,999", 
											"$175,000 - $199,999", 
											"$200,000 - $249,999", 
											"$250,000 - $299,999", 
											"$300,000 - $399,999", 
											"$400,000 - $499,999", 
											"$500,000 or more");
	private List<Long> numbersPerValue; 
	private Map<String, Long> valuesAndAmounts; 
	
	public HouseValueRecord() {
		numbersPerValue = new ArrayList<Long>(); 
		valuesAndAmounts = new LinkedHashMap<String, Long>(); 
		//0 out the values and add the keys to the linkedhashmap
		for (String key : HOUSING_VALUES) {
			valuesAndAmounts.put(key, 0l);
		}
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
	
	public List<String> getHousingValues() {
		return HOUSING_VALUES; 
	}
	public List<Long> getNumbersPerValue() {
		return numbersPerValue; 
	}
	public void setNumbersPerValue(ArrayList<Long> nums) {
		this.numbersPerValue = nums; 
	}
	public Map<String, Long> getValuesAndAmounts() {
		return valuesAndAmounts; 
	}
	public void setValuesAndAmounts(Map<String, Long> valsAndAmounts) {
		this.valuesAndAmounts = valsAndAmounts; 
	}
	
//	private int calculateMedian() {
//		int median = getValuesAndAmounts().values().size() / 2;
//		
//		return median; 
//	}
	
	private String computeMedian() {
		//Map<String, Long> numbersAndValues = new LinkedHashMap<String, Long>(); 
		//int counter = 0; 
//		for (Long l : numbers) {
//			numbersAndValues.put(HouseValueRecord.HOUSING_VALUES.get(counter), l);
//			counter++; 
//		}
//		Map<String, Long> sorted = new LinkedHashMap<String, Long>(); 
//		List<Map.Entry<String, Long>> list = new LinkedList<Map.Entry<String, Long>>(valuesAndAmounts.entrySet()); 
//		
//		Collections.sort(list, new Comparator<Map.Entry<String, Long>>() {
//			public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
//				return (o1.getValue().compareTo(o2.getValue()));
//			}
//		});
//		
//		for (Map.Entry<String, Long> entry : list) {
//			sorted.put(entry.getKey(), entry.getValue());
//		}
		
//		return sorted; 
		
		ArrayList<Long> numbers = new ArrayList<Long>(valuesAndAmounts.values());
		Long total = 0l; 
		for (Long l : numbers) {
			total += l; 
		}
		
		Long median = total / 2; 
		Long temp = 0l; 
		for (Map.Entry<String, Long> entry : valuesAndAmounts.entrySet()) {
			temp += entry.getValue(); 
			if (temp >= median) {
				return entry.getKey(); 
			}
		}
		
		return HOUSING_VALUES.get(HOUSING_VALUES.size() - 1);
		
	}
	

	@Override
	public void readFields(DataInput dataInput) throws IOException {
		for (String key : valuesAndAmounts.keySet()) {
			valuesAndAmounts.put(key, dataInput.readLong());
		}
		
	}

	@Override
	public void write(DataOutput dataOutput) throws IOException {
		for (Long l : valuesAndAmounts.values()) {
			dataOutput.writeLong(l);
		}
		
	}
	
	@Override
	public String toString() {
//		Map<String, Long> sortedValues = new LinkedHashMap<String, Long>(); 
//		sortedValues = sortAndStore(); 
//		
//		int median = sortedValues.values().size() / 2;  
//		List<Long> temp = new ArrayList<Long>(sortedValues.values());
//		Long medianValue = temp.get(sortedValues.values().size() - 1);
//		String medianRange = ""; 
//		for (Map.Entry<String, Long> entry : sortedValues.entrySet()) {
//			if (entry.getValue().equals(medianValue)) {
//				medianRange = entry.getKey(); 
//				break; 
//			}
//		}
		String medianRange = computeMedian(); 
		Long medianValue = 0l; 
		StringBuilder sb = new StringBuilder(); 
		
		sb.append("Ordering of housing values and number in order (for testing):" + "\n"); 
		
		Set s = valuesAndAmounts.entrySet(); 
		Iterator i = s.iterator(); 
		
		while(i.hasNext()) {
			Map.Entry e = (Map.Entry)i.next(); 
			sb.append(e.getKey() + ": ");
			sb.append(e.getValue() + "\n");
		}
		
		sb.append("\n");
		sb.append("Median range: ");
		sb.append(medianRange + "\n");
		
		
		return sb.toString(); 
		
		
	}

}
