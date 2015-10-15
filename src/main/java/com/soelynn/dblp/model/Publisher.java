package com.soelynn.dblp.model;

import org.apache.commons.lang3.StringEscapeUtils;

public class Publisher {

	private static int id_counter = 1;
	
	private int publisher_id;
	private String name;
	
	public Publisher(String name) {
		publisher_id = id_counter++;
		this.name = name;
	}
	
	public int getId() {
		return publisher_id;
	}
	
	public String getName() {
		return StringEscapeUtils.escapeCsv(name);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public static String getColumnNameList() {
		return "publisher_id,name\n";
	}
	
	public String toString() {
		return String.format("%d,%s\n", publisher_id, getName());
	}
	
}
