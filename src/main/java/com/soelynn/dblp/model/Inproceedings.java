package com.soelynn.dblp.model;

import org.apache.commons.lang3.StringEscapeUtils;

public class Inproceedings {
	
	private int pubid;
	private String booktitle;
	
	public Inproceedings(int pubid) {
		this.pubid = pubid;
	}

	public String getBooktitle() {
		return StringEscapeUtils.escapeCsv(booktitle);
	}

	public void setBooktitle(String booktitle) {
		this.booktitle = booktitle;
	}

	public int getPubid() {
		return pubid;
	}
	
	public static String getColumnNameList() {
		return "pubid,booktitle\n";
	}
	
	public String toString() {
		return String.format("%d,%s\n", getPubid(), getBooktitle());
	}
	
}
