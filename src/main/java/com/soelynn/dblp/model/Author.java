package com.soelynn.dblp.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Author {

	private static int id_counter = 1;
	
	private int id;
	private String key;
	private List<String> names;
	private String mdate;
	private int year;
	private String title;
	
	public Author() {
		this.id = id_counter++;
		this.names = new ArrayList<String>();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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
	
	public String getMDate() {
		return mdate;
	}
	
	public void setMDate(String mdate) {
		this.mdate = mdate;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String toString() {
		return String.format("Author\t: %s, %s, %s, %s, %d", StringUtils.join(names, " | "), key, title, mdate, year);
	}
	
}
