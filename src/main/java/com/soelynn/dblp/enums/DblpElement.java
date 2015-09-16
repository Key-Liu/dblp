package com.soelynn.dblp.enums;

import java.util.HashMap;

public enum DblpElement {
	
	ARTICLE("article"),
	INPROCEEDINGS("inproceedings"),
	PROCEEDINGS("proceedings"),
	BOOK("book"),
	INCOLLECTION("incollection"),
	PHDTHESIS("phdthesis"),
	MASTERTHESIS("mastersthesis"),
	WWW("www");
	
	/**
     * A mapping between the element name and its corresponding DblpElement to facilitate lookup by element_name.
     */
	private static HashMap<String, DblpElement> elementNameToDblpElementMapping;
	
	private String name;
	
	private DblpElement(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return name;
	}
	
	public static DblpElement getDblpElement(String element_name) {
		if(elementNameToDblpElementMapping == null) {
			elementNameToDblpElementMapping = new HashMap<String, DblpElement>();
			
			for(DblpElement element: DblpElement.values()) {
				elementNameToDblpElementMapping.put(element.getName(), element);
			}
		}
		
		return elementNameToDblpElementMapping.get(element_name);
	}
}
