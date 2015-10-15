package com.soelynn.dblp.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

public class Person {

	private static int id_counter = 1;
	
	private int id;
	private List<String> names;
	private String title;
	
	public Person() {
		this.id = id_counter++;
		this.names = new ArrayList<String>();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return StringEscapeUtils.escapeCsv(title);
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getNames() {
		return names;
	}
	
	public void setNames(List<String> names) {
		this.names = names;
	}
	
	public boolean addName(String name) {
		return this.names.add(name);
	}
	
	public static String getColumnNameList() {
		return "pid,title\n";
	}
	
	public String toString() {
		return String.format("%d,%s\n", id, getTitle());
	}
	
}
