package cs455.hadoop.mappers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import cs455.hadoop.records.HouseValueRecord;
import cs455.hadoop.util.ParsingUtil;

public class HouseValueMapper extends Mapper<LongWritable, Text, Text, HouseValueRecord> {

	
	@Override
	protected void map(LongWritable keyIn, Text valueIn, Context context) throws IOException, InterruptedException {
		String text = valueIn.toString(); 
		String summaryLevel = ParsingUtil.summaryLevel(text);
		
		if (summaryLevel.equals("100")) {
			String state = ParsingUtil.state(text);
			Long logicalRecordPartNumber = ParsingUtil.logicalRecordPartNumber(text);
			Long totalPartsInRecord = ParsingUtil.totalPartsInRecord(text);
			
			if (logicalRecordPartNumber.equals(totalPartsInRecord)) {
				HouseValueRecord houseValueRecord = new HouseValueRecord(); 
				houseValueRecord.setLogicalRecordPartNumber(logicalRecordPartNumber);
				houseValueRecord.setTotalPartsInRecord(totalPartsInRecord);
				
				//get all the numbers for each value and store in an array list
				//ArrayList<Long> numbers = getNumbersPerValue(text); 
				Map<String, Long> unsortedValues = getNumbersPerValue(text); 
				houseValueRecord.setValuesAndAmounts(unsortedValues);
				
				
				context.write(new Text(state), houseValueRecord);
			}
		}
	}
	
	
	private Map<String, Long> getNumbersPerValue(String text) {
		Long temp = 0l; 
		Map<String, Long> numbers = new LinkedHashMap<String, Long>();  
		int counter = 0; 
		for (int i = 2928; i < 3100; i+=9) {
			temp = Long.parseLong(text.substring(i, i + 9));
			numbers.put(HouseValueRecord.HOUSING_VALUES.get(counter), temp); 
			counter++; 
		}
		
		return numbers; 
	}
	
	private Map<String, Long> sortAndStore(ArrayList<Long> numbers) {
		Map<String, Long> numbersAndValues = new LinkedHashMap<String, Long>(); 
		int counter = 0; 
		for (Long l : numbers) {
			numbersAndValues.put(HouseValueRecord.HOUSING_VALUES.get(counter), l);
			counter++; 
		}
		Map<String, Long> sorted = new LinkedHashMap<String, Long>(); 
		List<Map.Entry<String, Long>> list = new LinkedList<Map.Entry<String, Long>>(numbersAndValues.entrySet()); 
		
		Collections.sort(list, new Comparator<Map.Entry<String, Long>>() {
			public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
				return (o1.getValue().compareTo(o2.getValue()));
			}
		});
		
		for (Map.Entry<String, Long> entry : list) {
			sorted.put(entry.getKey(), entry.getValue());
		}
		
		return sorted; 
	}
}
