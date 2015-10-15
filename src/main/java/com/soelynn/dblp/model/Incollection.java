package com.soelynn.dblp.model;

import org.apache.commons.lang3.StringEscapeUtils;

public class Incollection {

	private int pubid;
	private String isbn;
	private String publisher;
	private String booktitle;
	
	public Incollection(int pubid) {
		this.pubid = pubid;
	}

	public int getId() {
		return pubid;
	}
	
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPublisher() {
		return StringEscapeUtils.escapeCsv(publisher);
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public String getBooktitle() {
		return StringEscapeUtils.escapeCsv(booktitle);
	}

	public void setBooktitle(String booktitle) {
		this.booktitle = booktitle;
	}

	public static String getColumnNameList() {
		return "pubid,isbn,booktitle\n";
	}
	
	public String toString() {
		return String.format("%d,%s,%s\n", getId(), (getIsbn()!=null)?getIsbn():"NULL", getBooktitle());
	}
	
}
