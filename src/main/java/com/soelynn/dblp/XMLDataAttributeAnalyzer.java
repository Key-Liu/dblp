package com.soelynn.dblp;

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
			private DblpElement elementBegan = null;

			public void startElement(String uri, String localName, String element_name, Attributes attributes)
					throws SAXException {
				DblpElement element = DblpElement.getDblpElement(element_name);
				if (element != null) {
					elementBegan = element;
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
				}

			}

			public void endElement(String uri, String localName, String element_name) throws SAXException {
				DblpElement element = DblpElement.getDblpElement(element_name);

				if (elementBegan == element) {
					elementBegan = null;
				}

			}

			public void startDocument() throws SAXException {
				attributesMap = new HashMap<DblpElement, HashMap<String, Integer>>();
			}

			public void endDocument() throws SAXException {
				
				List<String> commonAttributes = Arrays.asList("ee", "year", "author", "title", "url");
				
				System.out.println("|Element|Attribute|Count|");
				System.out.println("|---|---|---|");

				for (DblpElement element : attributesMap.keySet()) {
					System.out.println("|`" + element + "`|");

					HashMap<String, Integer> attributeCountMap = attributesMap.get(element);

					for (String attribute : attributeCountMap.keySet()) {
						
						if(commonAttributes.contains(attribute)) {
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
