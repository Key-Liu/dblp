package com.soelynn.dblp.model;

import org.apache.commons.lang3.StringEscapeUtils;

public class PersonName {

	private static int id_counter = 1;
	
	private int pnid;
	private int pid;
	private String name;
	
	public PersonName(int pid, String name) {
		pnid = id_counter++;
		this.pid = pid;
		this.name = name;
	}
	
	public int getPid() {
		return pid;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return StringEscapeUtils.escapeCsv(name);
	}
	
	public static String getColumnNameList() {
		return "pid,pnid,name\n";
	}
	
	public String toString() {
		return String.format("%d,%d,%s\n", pid, pnid, getName());
	}
	
}
