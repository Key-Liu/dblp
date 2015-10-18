package com.soelynn.dblp.model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;

import com.soelynn.dblp.enums.PublicationType;
import com.soelynn.dblp.util.RomanNumeral;

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
		Pattern pattern_roman_numeral = Pattern.compile(
				"(?=[MDCLXVI])M*(C[MD]|D?C*)(X[CL]|L?X*)(I[XV]|V?I*)(-|_)(?=[MDCLXVI])M*(C[MD]|D?C*)(X[CL]|L?X*)(I[XV]|V?I*)");
		Pattern pattern_1 = Pattern.compile("[a-zA-Z]+\\d+");
		Pattern pattern_2 = Pattern.compile("\\d+[a-zA-Z]");
		Pattern pattern_3 = Pattern.compile("(IS-|IS |it-|IT-|C-|V-)\\d+");
		Pattern pattern_4 = Pattern.compile("[ag]-[ch]");
		String original_str = new String(pages);

		// Remove pattern such as 12:1-12:4, 34.1-34.20, B01:12-B012:12,
		// 1e:1-1e:3
		// The result will be 12:1-12:4 -> 1-4
		pages = pages.replaceAll("[a-zA-Z]*\\d+[a-zA-Z]*(:|\\.|/)", "");

		String[] page_ranges = pages.split(",|&");

		if (!pages.contains("-")) {
			no_of_pages++;
			return no_of_pages;
		}

		for (String page_range : page_ranges) {

			try {
				Matcher match_roman = pattern_roman_numeral.matcher(page_range.toUpperCase());
				Matcher match_1 = pattern_1.matcher(page_range);
				Matcher match_2 = pattern_2.matcher(page_range);
				Matcher match_3 = pattern_3.matcher(page_range);
				Matcher match_4 = pattern_4.matcher(page_range);

				if (match_roman.find()) {
					// Some computation
					String[] roman_numerals = page_range.split("-|_");
					int start = RomanNumeral.valueOf(roman_numerals[0]);
					int end = RomanNumeral.valueOf(roman_numerals[1]);
					
					page_range = start + "-" + end;
				} else if (page_range.matches("\\d+[A-Z]-\\d+[A-Z]-\\d+")) { // 90210H-90210H-12
					page_range = page_range.replaceAll("\\d+[A-Z]-\\d+[A-Z]-", "");
					no_of_pages += Integer.parseInt(page_range);
					continue;
				} else if (match_1.find()) { // [a-zA-Z]+\d+
					page_range = page_range.replaceAll("[a-zA-Z]+", "");
				} else if (match_2.find()) { // \d+[a-zA-Z]
					if (page_range.matches("\\d+[a-zA-Z]-\\d+[a-zA-Z]") | page_range.matches("a-[a-z]")) { // e.g.
																											// 14a-14i
																											// and
																											// a-i
						page_range = page_range.replaceAll("\\d+", "");
						String[] splitted = page_range.split("-");
						char start = splitted[0].charAt(0);
						char end = splitted[1].charAt(0);
						no_of_pages += (int) end - (int) start + 1;
						continue;
					} else if (page_range.matches("\\d+[a-z]-\\d+")) {
						page_range = page_range.replaceAll("[a-z]", "");
					} else if (page_range.matches("\\d+-\\d+[a-z]")) {
						page_range = page_range.replaceAll("\\d+-\\d+", "");
						char start = 'a';
						char end = page_range.charAt(0);
						no_of_pages += (int) end - (int) start + 1;
						continue;
					}
				} else if (match_3.find()) { // e.g. pattern IS-12-IS-12 and IS
												// 2-4
					page_range = page_range.replaceAll("(IS-|IS |it-|IT-|C-|V-)", "");
				} else if (match_4.find()) { // a-c and g-h
					String[] ranges = page_range.split("-");
					char start = ranges[0].charAt(0);
					char end = ranges[1].charAt(0);
					
					no_of_pages += (int) end - (int) start + 1;					
					continue;
				}

				String[] start_end = page_range.split("-");

				// For normal format, e.g. 1-10
				int start = Integer.parseInt(start_end[0].trim());

				if (start_end.length > 1) {
					int end = Integer.parseInt(start_end[1].trim());
					no_of_pages += Math.abs(end - start) + 1;
				} else {
					no_of_pages++;
				}
			} catch (Exception exception) {
				no_of_pages++;
			}
		}

		return no_of_pages;

	}
}
