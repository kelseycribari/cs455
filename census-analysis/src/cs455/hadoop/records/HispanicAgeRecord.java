package cs455.hadoop.records;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class HispanicAgeRecord implements Writable {
	
	private long logicalRecordPartNumber = 0; 
	private long totalPartsInRecord = 0; 
	
	private long totalFemaleHispanicPopulation = 0; 
	private long totalMaleHispanicPopulation = 0; 
	
	private long totalHispanicPopulation = 0; 
	
	private long females18AndBelow = 0; 
	private long males18AndBelow = 0; 
	private long pop18AndBelow = 0; 
	private long femalesBetween19And29 = 0; 
	private long malesBetween19And29 = 0; 
	private long pop19To29 = 0; 
	private long femalesBetween30And39 = 0; 
	private long malesBetween30And39 = 0; 
	private long pop30To39 = 0; 
	
	public HispanicAgeRecord() {
		
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
	public long getTotalFemaleHispanicPopulation() {
		return this.totalFemaleHispanicPopulation;
	}
	public void setFemaleHispanicPopulation(long num) {
		this.totalFemaleHispanicPopulation = num; 
	}
	public long getMaleHispanicPopulation() {
		return this.totalMaleHispanicPopulation;
	}
	public void setMaleHispanicPopulation(long num) {
		this.totalMaleHispanicPopulation = num; 
	}
	public long getFemales18AndBelow() {
		return this.females18AndBelow;
	}
	public void setFemales18AndBelow(long num) {
		this.females18AndBelow = num;
	}
	public long getMales18AndBelow() {
		return this.males18AndBelow;
	}
	public void setMales18AndBelow(long num) {
		this.males18AndBelow = num; 
	}
	public long getFemalesBetween19And29() {
		return this.femalesBetween19And29;
	}
	public void setFemalesBetween19And29(long num) {
		this.femalesBetween19And29 = num; 
	}
	public long getMalesBetween19And29() {
		return this.malesBetween19And29; 
	}
	public void setMalesBetween19And29(long num) {
		this.malesBetween19And29 = num; 
	}
	public long getFemalesBetween30And39() {
		return this.femalesBetween30And39;
	}
	public void setFemalesBetween30And39(long num) {
		this.femalesBetween30And39 = num; 
	}
	public long getMalesBetween30And39() {
		return this.malesBetween30And39; 
	}
	public void setMalesBetween30And39(long num) {
		this.malesBetween30And39 = num; 
	}
	
	public long getBelow18() {
		return this.pop18AndBelow;
	}
	public void setBelow18(long num) {
		this.pop18AndBelow = num; 
	}
	public long get19To29() {
		return this.pop19To29;
	}
	public void set19To29(long num) {
		this.pop19To29 = num; 
	}
	public long get30To39() {
		return this.pop30To39;
	}
	public void set30To39(long num) {
		this.pop30To39 = num; 
	}
	public long getTotalHispanicPopulation() {
		return this.totalHispanicPopulation;
	}
	public void setTotalHispanicPopulation(long num) {
		this.totalHispanicPopulation = num; 
	}
	
	private double percentageFemales18AndBelow() {
		double total = getMaleHispanicPopulation() + getTotalFemaleHispanicPopulation(); 
		double females = getFemales18AndBelow(); 
		double percentage = (females / total) * 100.0; 
		return percentage; 
	}
	private double percentageMales18AndBelow() {
		double total = getMaleHispanicPopulation() + getTotalFemaleHispanicPopulation();
		double males = getMales18AndBelow(); 
		double percentage = (males / total) * 100.0; 
		return percentage; 
	}
	private double percentageFemales19To29() {
		double total = getMaleHispanicPopulation() + getTotalFemaleHispanicPopulation();
		double females = getFemalesBetween19And29(); 
		double percentage = (females / total) * 100.0; 
		return percentage; 
	}
	private double percentageMales19To29() {
		double total = getMaleHispanicPopulation() + getTotalFemaleHispanicPopulation();
		double males = getMalesBetween19And29(); 
		double percentage = (males / total) * 100.0; 
		return percentage; 
	}
	private double percentageFemales30To39() {
		double total = getMaleHispanicPopulation() + getTotalFemaleHispanicPopulation();
		double females = getFemalesBetween30And39(); 
		double percentage = (females / total) * 100.0; 
		return percentage; 
	}
	private double percentageMales30To39() {
		double total = getMaleHispanicPopulation() + getTotalFemaleHispanicPopulation();
		double males = getMalesBetween30And39(); 
		double percentage = (males / total) * 100.0; 
		return percentage; 
	}
	

	@Override
	public void readFields(DataInput dataInput) throws IOException {
		//totalFemaleHispanicPopulation = dataInput.readLong(); 
		//totalMaleHispanicPopulation = dataInput.readLong(); 
		females18AndBelow = dataInput.readLong(); 
		males18AndBelow = dataInput.readLong(); 
		//pop18AndBelow = dataInput.readLong(); 
		femalesBetween19And29 = dataInput.readLong(); 
		malesBetween19And29 = dataInput.readLong(); 
		//pop19To29 = dataInput.readLong(); 
		femalesBetween30And39 = dataInput.readLong(); 
		malesBetween30And39 = dataInput.readLong(); 
		//pop30To39 = dataInput.readLong(); 
		//totalHispanicPopulation = dataInput.readLong();
		totalFemaleHispanicPopulation = dataInput.readLong(); 
		totalMaleHispanicPopulation = dataInput.readLong(); 
	}

	@Override
	public void write(DataOutput dataOutput) throws IOException {
		//dataOutput.writeLong(totalFemaleHispanicPopulation);
		//dataOutput.writeLong(totalMaleHispanicPopulation);
		dataOutput.writeLong(females18AndBelow);
		dataOutput.writeLong(males18AndBelow);
		//dataOutput.writeLong(pop18AndBelow);
		dataOutput.writeLong(femalesBetween19And29);
		dataOutput.writeLong(malesBetween19And29);
		//dataOutput.writeLong(pop19To29);
		dataOutput.writeLong(femalesBetween30And39);
		dataOutput.writeLong(malesBetween30And39);
		//dataOutput.writeLong(pop30To39);
		dataOutput.writeLong(totalFemaleHispanicPopulation);
		dataOutput.writeLong(totalMaleHispanicPopulation);
		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		double percentFemales18AndBelow = percentageFemales18AndBelow();
		//long totalHispanicPopulation = getTotalFemaleHispanicPopulation() + getMaleHispanicPopulation();
		//long percentFemales18AndBelow = (getFemales18AndBelow() / totalHispanicPopulation) * 100; 
		double percentMales18AndBelow = percentageMales18AndBelow(); 
		//long percentMales18AndBelow = (getMales18AndBelow() / totalHispanicPopulation) * 100; 
		double percentFemales19To29 = percentageFemales19To29();
		//long percentFemales19To29 = (getFemalesBetween19And29() / totalHispanicPopulation) * 100; 
		double percentMales19To29 = percentageMales19To29();
		//long percentMales19To29 = (getMalesBetween19And29() / totalHispanicPopulation) * 100; 
		double percentFemales30To39 = percentageFemales30To39();
		//long percentFemales30To39 = (getFemalesBetween30And39() / totalHispanicPopulation) * 100; 
		double percentMales30To39 = percentageMales30To39();
		//long percentMales30To39 = (getMalesBetween30And39() / totalHispanicPopulation) * 100; 
		//this.totalHispanicPopulation = getFemales18AndBelow() + getMales18AndBelow() + getFemalesBetween19And29() 
									//+ getMalesBetween19And29() + getFemalesBetween30And39() + getMalesBetween30And39(); 
		 
		sb.append("Female Hispanic population 18 (inclusive) and below: "); 
		sb.append(getFemales18AndBelow() + "\n");
		sb.append("Male Hispanic population 18 (inclusive) and below: "); 
		sb.append(getMales18AndBelow() + "\n");
		sb.append("Female Hispanic population between 19 (inclusive) and 29 (inclusive): ");
		sb.append(getFemalesBetween19And29()+ "\n");
		sb.append("Male Hispanic population between 19 (inclusive) and 29 (inclusive): ");
		sb.append(getMalesBetween19And29()+ "\n");
		sb.append("Female Hispanic population between 30 (inclusive) and 39 (inclusive): ");
		sb.append(getFemalesBetween30And39() + "\n");
		sb.append("Male Hispanic population between 30 (inclusive) and 39 (inclusive): ");
		sb.append(getMalesBetween30And39() + "\n");
		sb.append("Total Female Hispanic Population: "); 
		sb.append(getTotalFemaleHispanicPopulation() + "\n");
		sb.append("Total Male Hispanic Population: ");
		sb.append(getMaleHispanicPopulation() + "\n");
		sb.append("Percentage of female population 18 and below: "); 
		sb.append(percentFemales18AndBelow + "\n");
		sb.append("Percentage of male population 18 and below: "); 
		sb.append(percentMales18AndBelow + "\n");
		sb.append("Percentage of female population between 19 and 29: ");
		sb.append(percentFemales19To29 + "\n");
		sb.append("Percentage of male population between 19 and 29: ");
		sb.append(percentMales19To29 + "\n");
		sb.append("Percentage of female population between 30 and 39: ");
		sb.append(percentFemales30To39 + "\n");
		sb.append("Percentage of male population between 30 and 39: ");
		sb.append(percentMales30To39 + "\n");
		
		return sb.toString(); 
		
		
	}
	
	
	

}
