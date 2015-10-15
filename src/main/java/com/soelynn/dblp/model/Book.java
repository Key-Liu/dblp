package com.soelynn.dblp.model;

import org.apache.commons.lang3.StringEscapeUtils;

public class Book {

	private int pubid;
	private String isbn;
	
	public Book(int pubid) {
		this.pubid = pubid;
	}
	
	public int getId() {
		return pubid;
	}

	public String getIsbn() {
		return StringEscapeUtils.escapeCsv(isbn);
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public static String getColumnNameList() {
		return "pubid,isbn\n";
	}
	
	public String toString() {
		return String.format("%d,%s\n", getId(), getIsbn());
	}
	
}
