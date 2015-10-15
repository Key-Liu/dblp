package com.soelynn.dblp.model;

import org.apache.commons.lang3.StringEscapeUtils;

public class Article {

	private int pubid;
	private String journal;
	private String month;
	private String volume;
	private String number;
	
	public Article(int pubid) {
		this.pubid = pubid;
	}
	
	public int getPubid() {
		return pubid;
	}

	public String getJournal() {
		return StringEscapeUtils.escapeCsv(journal);
	}
	
	public void setJournal(String journal) {
		this.journal = journal;
	}
	
	public String getMonth() {
		return StringEscapeUtils.escapeCsv(month);
	}
	
	public void setMonth(String month) {
		this.month = month;
	}
	
	public String getVolume() {
		return StringEscapeUtils.escapeCsv(volume);
	}
	
	public void setVolume(String volume) {
		this.volume = volume;
	}
	
	public String getNumber() {
		return StringEscapeUtils.escapeCsv(number);
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public static String getColumnNameList() {
		return "pubid,journal,month,volume,number\n";
	}
	
	public String toString() {
		return String.format("%d,%s,%s,%s,%s\n", getPubid(), getJournal(), (getMonth()!=null)?getMonth():"NULL", getVolume(), (getNumber()!=null)?getNumber():"NULL");
	}
	
}
