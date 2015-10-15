package com.soelynn.dblp.enums;

public enum PublicationType {
	
	ARTICLE(1),
	INPROCEEDINGS(2),
	INCOLLECTION(3),
	BOOK(4);
	
	private int id;
	
	private PublicationType(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
