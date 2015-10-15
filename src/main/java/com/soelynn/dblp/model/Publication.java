package com.soelynn.dblp.model;

import java.util.ArrayList;

import org.apache.commons.lang3.StringEscapeUtils;

import com.soelynn.dblp.enums.PublicationType;

public class Publication {

	private static int id_counter = 1;

	private int pubid;
	private String pubkey;
	private String title;
	private int year;
	private PublicationType publtype;
	private int publisher_id;
	private String pages;
	private int no_of_pages;
	private ArrayList<String> authors;
	private ArrayList<String> editors;

	public Publication() {
		this.pubid = id_counter++;
		authors = new ArrayList<String>();
		editors = new ArrayList<String>();
	}

	public int getPubid() {
		return pubid;
	}

	public String getPubkey() {
		return StringEscapeUtils.escapeCsv(pubkey);
	}

	public void setPubkey(String pubkey) {
		this.pubkey = pubkey;
	}

	public String getPages() {
		return StringEscapeUtils.escapeCsv(pages);
	}

	public void setPages(String pages) {
		this.pages = pages;
		setNo_of_pages(countNoOfPages(pages));
	}

	public int getNo_of_pages() {
		return no_of_pages;
	}

	public void setNo_of_pages(int no_of_pages) {
		this.no_of_pages = no_of_pages;
	}

	public String getTitle() {
		return StringEscapeUtils.escapeCsv(title);
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public PublicationType getPubltype() {
		return publtype;
	}

	public void setPubltype(PublicationType publtype) {
		this.publtype = publtype;
	}

	public int getPublisherId() {
		return publisher_id;
	}

	public void setPublisherId(int publisher_id) {
		this.publisher_id = publisher_id;
	}

	public ArrayList<String> getAuthors() {
		return authors;
	}

	public void addAuthor(String author) {
		authors.add(author);
	}

	public ArrayList<String> getEditors() {
		return editors;
	}

	public void addEditor(String editor) {
		editors.add(editor);
	}

	public static String getColumnNameList() {
		return "pubid,pubkey,title,year,publtype_id,publisher_id,pages,no_of_pages\n";
	}

	public String toString() {
		return String.format("%d,%s,%s,%d,%d,%s,%s,%d\n", pubid, getPubkey(), getTitle(), getYear(),
				getPubltype().getId(), (getPublisherId() == 0) ? "NULL" : getPublisherId(), getPages(),
				getNo_of_pages());
	}

	private static int countNoOfPages(String pages) {
		int no_of_pages = 0;
		String[] page_ranges = pages.split(",");

		if (!pages.contains("-")) {
			no_of_pages++;
			return no_of_pages;
		}

		try {
			for (String page_range : page_ranges) {

				String[] start_end = page_range.split("-");

				if (start_end[0].contains(":") | start_end[0].contains(".")) { // For special format,
													// 12:1-12:4
													// 34.1-34.20
					if (start_end.length > 1) {
						String[] range_split = start_end[0].split(":|\\.");
						int start = Integer.parseInt(range_split[1]);

						range_split = start_end[1].split(":|\\.");
						int end = Integer.parseInt(range_split[1]);
						no_of_pages += Math.abs(end - start) + 1;
					} else {
						no_of_pages++;
					}

				} else { // For normal format, e.g. 1-10
					int start = Integer.parseInt(start_end[0].trim());

					if (start_end.length > 1) {
						int end = Integer.parseInt(start_end[1]);
						no_of_pages += Math.abs(end - start) + 1;
					} else {
						no_of_pages++;
					}
				}
			}
		} catch (Exception exception) {
//			exception.printStackTrace();
			System.out.println(pages);
			no_of_pages++;
		}

		return no_of_pages;
	}

	public static void main(String[] args) {
		System.out.println(countNoOfPages("68-72, 80"));
		System.out.println(countNoOfPages("34.1-34.20"));
		System.out.println(countNoOfPages("B121-B132"));

	}

}
