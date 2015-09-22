package com.soelynn.dblp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.soelynn.dblp.enums.DblpElement;

public class XMLDataAttributeAnalyzer {

	public static void main(String[] args) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();

		DefaultHandler handler = new DefaultHandler() {

			private HashMap<DblpElement, HashMap<String, Integer>> attributesMap;
			private HashMap<DblpElement, HashMap<String, ArrayList<String>>> keysMap;
			private DblpElement elementBegan = null;

			// Tag tracking variables
			private boolean isProceedingElement = false;
			private boolean isProceedingElement_title = false;

			public void startElement(String uri, String localName, String element_name, Attributes attributes)
					throws SAXException {
				DblpElement element = DblpElement.getDblpElement(element_name);
				if (element != null) {
					elementBegan = element;

					String[] subkeys = attributes.getValue("key").split("/");

					// Collecting the key attribute of each DblpElement
					HashMap<String, ArrayList<String>> key_mapping = keysMap.get(element);
					if (key_mapping == null) {
						key_mapping = new HashMap<String, ArrayList<String>>();
					}

					ArrayList<String> keyList = key_mapping.get(subkeys[0]);
					if (keyList == null) {
						keyList = new ArrayList<String>();
					}

					keyList.add(subkeys[1]);

					key_mapping.put(subkeys[0], keyList);
					keysMap.put(element, key_mapping);

					switch (element) {
					case PROCEEDINGS:
						if (subkeys[0].equals("conf")) {
							isProceedingElement = true;
						}
						break;
					default:
						break;
					}

				} else if (elementBegan != null) {

					HashMap<String, Integer> attributesCountMap = attributesMap.get(elementBegan);
					if (attributesCountMap == null) {
						attributesCountMap = new HashMap<String, Integer>();
					}

					int attributeCount = attributesCountMap.containsKey(element_name)
							? attributesCountMap.get(element_name) : 0;
					attributeCount++;
					attributesCountMap.put(element_name, attributeCount);
					attributesMap.put(elementBegan, attributesCountMap);

					// Tag tracking logic
					if (isProceedingElement) {
						if (element_name.equals("title")) {
							isProceedingElement_title = true;
						}
					}
				}
			}

			public void endElement(String uri, String localName, String element_name) throws SAXException {
				DblpElement element = DblpElement.getDblpElement(element_name);

				if (elementBegan == element) {
					elementBegan = null;
				}

			}

			public void characters(char ch[], int start, int length) throws SAXException {

				if (isProceedingElement_title) {
					String title = new String(ch, start, length);
					System.out.println(title);

					isProceedingElement = false;
					isProceedingElement_title = false;
				}

			}

			public void startDocument() throws SAXException {
				attributesMap = new HashMap<DblpElement, HashMap<String, Integer>>();
				keysMap = new HashMap<DblpElement, HashMap<String, ArrayList<String>>>();
			}

			public void endDocument() throws SAXException {
				
			}

			////////////////////////////////////////////////////////
			/*
			 * Functions to print out different info from data read from
			 * dblp.xml
			 */
			////////////////////////////////////////////////////////

			private void printElementKeyInMarkDownFormat() {
				System.out.println("|Element|Key|");
				System.out.println("|---|---|");

				for (DblpElement element : keysMap.keySet()) {
					HashMap<String, ArrayList<String>> keys_mapping = keysMap.get(element);

					System.out.print("|`" + element + "`|");
					for (String major_key : keys_mapping.keySet()) {
						System.out.print(" `" + major_key + "`");
					}
					System.out.println(" |");

				}
			}

			private void printAttributeListInMarkDownFormat() {
				List<String> commonAttributes = Arrays.asList("ee", "year", "author", "title", "url");

				System.out.println("|Element|Attribute|Count|");
				System.out.println("|---|---|---|");

				for (DblpElement element : attributesMap.keySet()) {
					System.out.println("|`" + element + "`|");

					HashMap<String, Integer> attributeCountMap = attributesMap.get(element);

					for (String attribute : attributeCountMap.keySet()) {

						if (commonAttributes.contains(attribute)) {
							continue;
						}

						System.out.println("| |`" + attribute + "`|" + attributeCountMap.get(attribute) + "|");
					}
				}
			}

			private void printCommonAttributes() {
				Set<String> commonAttributes = null;

				for (DblpElement element : attributesMap.keySet()) {
					HashMap<String, Integer> attributeCountMap = attributesMap.get(element);

					if (commonAttributes == null) {
						commonAttributes = attributeCountMap.keySet();
					}

					commonAttributes.retainAll(attributeCountMap.keySet());
				}

				System.out.println(commonAttributes);
			}

			private void printElementAndAttributeCountInMarkDownFormat() {
				System.out.println("|Element|Attribute|Count|");
				System.out.println("|---|---|---|");

				for (DblpElement element : attributesMap.keySet()) {
					System.out.println("|`" + element + "`|");

					HashMap<String, Integer> attributeCountMap = attributesMap.get(element);

					for (String attribute : attributeCountMap.keySet()) {
						System.out.println("| |`" + attribute + "`|" + attributeCountMap.get(attribute) + "|");
					}
				}
			}
		};

		parser.parse("src/main/resources/dblp.xml", handler);
	}

}
