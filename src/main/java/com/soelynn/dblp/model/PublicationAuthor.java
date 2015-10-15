package com.soelynn.dblp.model;

public class PublicationAuthor {

	private int pid;
	private int pubid;
	
	public PublicationAuthor(int pid, int pubid) {
		this.pid = pid;
		this.pubid = pubid;
	}
	
	public static String getColumnNameList() {
		return "pid,pubid\n";
	}
	
	public String toString() {
		return String.format("%d,%d\n", pid, pubid);
	}

}
