package cs455.hadoop.mappers;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import cs455.hadoop.records.HispanicAgeRecord;
import cs455.hadoop.util.ParsingUtil;

public class HispanicAgeMapper extends Mapper<LongWritable, Text, Text, HispanicAgeRecord> {

	
	@Override 
	protected void map(LongWritable keyIn, Text valueIn, Context context) throws IOException, InterruptedException {
		String text = valueIn.toString(); 
		String summaryLevel = ParsingUtil.summaryLevel(text);
		
		if (summaryLevel.equals("100")) {
			String state = ParsingUtil.state(text);
			Long logicalRecordPartNumber = ParsingUtil.logicalRecordPartNumber(text);
			Long totalPartsInRecord = ParsingUtil.totalPartsInRecord(text);
			
			HispanicAgeRecord hispanicRecord = new HispanicAgeRecord(); 
			if (!logicalRecordPartNumber.equals(totalPartsInRecord)) {
				hispanicRecord.setLogicalRecordPartNumber(logicalRecordPartNumber);
				hispanicRecord.setTotalPartsInRecord(totalPartsInRecord);
				//need total population 18 and below 
//				Long males18AndBelow
//				hispanicRecord.setMales18AndBelow(males18AndBelow);
//				Long males19To29 = Long.parseLong(text.substring(beginIndex))
				
				Long females18AndBelow = getFemalesBelow18(text); 
				hispanicRecord.setFemales18AndBelow(females18AndBelow);
				Long males18AndBelow = getMalesBelow18(text); 
				hispanicRecord.setMales18AndBelow(males18AndBelow);
				
				Long females19To29 = getFemales19To29(text); 
				hispanicRecord.setFemalesBetween19And29(females19To29);
				Long males19To29 = getMales19To29(text); 
				hispanicRecord.setMalesBetween19And29(males19To29);
				
				Long females30To39 = getFemales30To39(text); 
				hispanicRecord.setFemalesBetween30And39(females30To39);
				Long males30To39 = getMales30To39(text); 
				hispanicRecord.setMalesBetween30And39(males30To39);
				
				Long totalFemaleHispanicPopulation = getTotalFemaleHispanicPopulation(text); 
				hispanicRecord.setFemaleHispanicPopulation(totalFemaleHispanicPopulation);
				Long totalMaleHispanicPopulation = getTotalMaleHispanicPopulation(text); 
				hispanicRecord.setMaleHispanicPopulation(totalMaleHispanicPopulation);
				
				context.write(new Text(state), hispanicRecord);
				
			}
		}
	}
	
	
	private Long getFemalesBelow18(String text) {
		
		Long females18AndBelow = 0l; 
		//Long total18AndBelow = 0l; 
		Long temp = 0l; 

		for (int j = 4143; j < 4252; j+=9) {
			temp = Long.parseLong(text.substring(j, j + 9));
			females18AndBelow += temp; 
			temp = 0l; 
		}
		//total18AndBelow = males18AndBelow + females18AndBelow;
		
		return females18AndBelow;  
	}
	
	private Long getMalesBelow18(String text) {
		
		Long temp = 0l; 
		Long males18AndBelow = 0l; 
		for (int i = 3864; i < 3973; i+=9) {
			temp = Long.parseLong(text.substring(i, i + 9));
			males18AndBelow += temp; 
			temp = 0l; 
		}
		
		return males18AndBelow; 
	}
	
	private Long getFemales19To29(String text) {
		 
		Long females19To29 = 0l; 
		//Long total19To29 = 0l; 
		Long temp = 0l; 
		
		for (int j = 4260; j < 4297; j+=9) {
			temp = Long.parseLong(text.substring(j, j + 9));
			females19To29 += temp; 
			temp = 0l; 
		}
		//total19To29 = males19To29 + females19To29; 
		
		return females19To29; 
	}
	
	private Long getMales19To29(String text) {
		Long males19To29 = 0l;
		Long temp = 0l; 
		for (int i = 3981; i < 4018; i+=9) {
			temp = Long.parseLong(text.substring(i, i + 9));
			males19To29 += temp; 
			temp = 0l; 
		}
		
		return males19To29; 
		
	}
	
	private Long getFemales30To39(String text) {
		 
		Long females30To39 = 0l; 
		//Long total30To39 = 0l; 
		Long temp = 0l; 
		
		
		for (int j = 4305; j < 4315; j+=9) {
			temp = Long.parseLong(text.substring(j, j + 9));
			females30To39 += temp; 
			temp = 0l; 
		}
		
		//total30To39 = males30To39 + females30To39; 
		
		return females30To39; 
	}
	
	private Long getMales30To39(String text) {
		Long males30To39 = 0l;
		Long temp = 0l; 
		for (int i = 4026; i < 4036; i+=9) {
			temp = Long.parseLong(text.substring(i, i + 9));
			males30To39 += temp;  
			temp = 0l; 
		}
		
		return males30To39; 
	}
	private Long getTotalFemaleHispanicPopulation(String text) {
		Long totalFemalePopulation = 0l;
		Long temp = 0l; 
		for (int j = 4143; j < 4414; j+=9) {
			temp = Long.parseLong(text.substring(j, j + 9));
			totalFemalePopulation += temp; 
			temp = 0l; 
		}
		
		return totalFemalePopulation; 
	}
	private Long getTotalMaleHispanicPopulation(String text) {
		Long totalMalePopulation = 0l; 
		//Long totalFemalePopulation = 0l; 
		//Long totalHispanicPopulation = 0l; 
		Long temp = 0l; 
		
		for (int i = 3864; i < 4135; i+=9) {
			temp = Long.parseLong(text.substring(i, i + 9));
			totalMalePopulation += temp; 
			temp = 0l; 
		}
		
		
		//totalHispanicPopulation = totalMalePopulation + totalFemalePopulation; 
		
		return totalMalePopulation; 
	}
}
