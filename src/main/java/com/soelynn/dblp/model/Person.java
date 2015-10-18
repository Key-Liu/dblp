package com.soelynn.dblp.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

public class Person {

	private static int id_counter = 1;
	
	private int id;
	private List<String> names;
	private Integer primaryname_id;
	private String title;
	
	public Person() {
		this.id = id_counter++;
		this.names = new ArrayList<String>();
		primaryname_id = null;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Integer getPrimaryname_id() {
		return primaryname_id;
	}

	public void setPrimaryname_id(Integer primaryname_id) {
		this.primaryname_id = primaryname_id;
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
		return "pid,title,primaryname_id\n";
	}
	
	public String toString() {
		return String.format("%d,%s,%d\n", id, getTitle(), getPrimaryname_id());
	}
	
}
