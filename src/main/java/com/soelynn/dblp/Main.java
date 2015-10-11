package com.soelynn.dblp;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.soelynn.dblp.enums.DblpElement;
import com.soelynn.dblp.model.Author;

public class Main {

	public static void main(String[] args) throws Exception {

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();

		DefaultHandler handler = new DefaultHandler() {

			// WWW element related flags
			private boolean isWWWElement = false;
			private boolean isWWW_author = false;
			private boolean isWWW_title = false;
			private boolean isWWW_url = false;
			private boolean isWWW_year = false;

			// Modeled Data
			private ArrayList<Author> authors = new ArrayList<Author>();
			private Author author_buffer = null;

			public void startElement(String uri, String localName, String element_name, Attributes attributes) {

				DblpElement element = DblpElement.getDblpElement(element_name);

				if (element != null) {
					switch (element) {
					case WWW:
						String key = attributes.getValue("key");
						if (key.contains("homepages")) { // This tag contains
															// author
															// info
							isWWWElement = true;
							author_buffer = new Author();
							author_buffer.setKey(attributes.getValue("key"));
							author_buffer.setMDate(attributes.getValue("mdate"));
						}
						break;
					default:
						break;
					}
				} else {

					if (isWWWElement) {
						if (element_name.equals("author")) {
							isWWW_author = true;
						} else if (element_name.equals("title")) {
							isWWW_title = true;
						} else if (element_name.equals("url")) {
							isWWW_url = true;
						} else if (element_name.equals("year")) {
							isWWW_year = true;
						}
					}
				}
			}

			public void endElement(String uri, String localName, String element_name) throws SAXException {

				DblpElement element = DblpElement.getDblpElement(element_name);

				if (element != null) {
					switch (element) {
					case WWW:
						if (isWWWElement) {
							authors.add(author_buffer);
							System.out.println(author_buffer);
							author_buffer = null;

							isWWWElement = false;
						}
						break;
					default:
						break;
					}
				}

			}

			public void characters(char ch[], int start, int length) throws SAXException {
				String value = new String(ch, start, length);

				if (isWWWElement) {
					if (isWWW_title) {
						author_buffer.setTitle(value);
						isWWW_title = false;
					} else if (isWWW_author) {
						author_buffer.addName(value);
						isWWW_author = false;
					} else if (isWWW_year) {
						author_buffer.setYear(Integer.parseInt(value));
						System.out.println(value);
						isWWW_year = false;
					}
				}
			}

			public void endDocument() throws SAXException {

			}

		};

		parser.parse("src/main/resources/dblp.xml", handler);
	}

}
