package com.soelynn.dblp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.soelynn.dblp.enums.DblpElement;
import com.soelynn.dblp.enums.PublicationType;
import com.soelynn.dblp.model.Article;
import com.soelynn.dblp.model.Book;
import com.soelynn.dblp.model.Incollection;
import com.soelynn.dblp.model.Inproceedings;
import com.soelynn.dblp.model.Person;
import com.soelynn.dblp.model.PersonName;
import com.soelynn.dblp.model.Publication;
import com.soelynn.dblp.model.PublicationAuthor;
import com.soelynn.dblp.model.PublicationEditor;
import com.soelynn.dblp.model.Publisher;

public class Main {

	public static void main(String[] args) throws Exception {

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();

		DefaultHandler handler = new DefaultHandler() {

			// Article Related flags
			private boolean isArticleElement = false;
			private boolean isArticle_author = false;
			private boolean isArticle_title = false;
			private boolean isArticle_pages = false;
			private boolean isArticle_month = false;
			private boolean isArticle_year = false;
			private boolean isArticle_volume = false;
			private boolean isArticle_journal = false;
			private boolean isArticle_number = false;

			// WWW element related flags
			private boolean isWWWElement = false;
			private boolean isWWW_author = false;
			private boolean isWWW_title = false;

			// Incollection element related flags
			private boolean isIncollectionElement = false;
			private boolean isIncollection_author = false;
			private boolean isIncollection_title = false;
			private boolean isIncollection_pages = false;
			private boolean isIncollection_year = false;
			private boolean isIncollection_isbn = false;
			private boolean isIncollection_booktitle = false;

			// Book element related flags
			private boolean isBookElement = false;
			private boolean isBook_author = false;
			private boolean isBook_title = false;
			private boolean isBook_pages = false;
			private boolean isBook_year = false;
			private boolean isBook_isbn = false;
			private boolean isBook_editor = false;
			private boolean isBook_publisher = false;

			// Inproceedings element related flags
			private boolean isInproceedingsElement = false;
			private boolean isInproceedings_author = false;
			private boolean isInproceedings_editor = false;
			private boolean isInproceedings_publisher = false;
			private boolean isInproceedings_title = false;
			private boolean isInproceedings_pages = false;
			private boolean isInproceedings_year = false;
			private boolean isInproceedings_booktitle = false;

			// Modeled Data
			private HashMap<String, Integer> publisher_id_map = new HashMap<String, Integer>();
			private HashMap<String, ArrayList<Integer>> person_name_id_mapping = new  HashMap<String, ArrayList<Integer>>();
			
			private ArrayList<Publication> publications = new ArrayList<Publication>();
			private ArrayList<Publisher> publishers = new ArrayList<Publisher>();
			private ArrayList<Article> articles = new ArrayList<Article>();
			private ArrayList<Incollection> incollections = new ArrayList<Incollection>();
			private ArrayList<Book> books = new ArrayList<Book>();
			private ArrayList<Inproceedings> inproceedingsList = new ArrayList<Inproceedings>();
			private ArrayList<PublicationAuthor> publicationAuthors = new ArrayList<PublicationAuthor>();
			private ArrayList<PublicationEditor> publicationEditors = new ArrayList<PublicationEditor>();
			
			private ArrayList<Person> persons = new ArrayList<Person>();
			private ArrayList<PersonName> personNameList = new ArrayList<PersonName>();

			private ArrayList<String> attribute_value_buffer = new ArrayList<String>();

			private Person author_buffer = null;
			private Publication publication_buffer = null;
			private Article article_buffer = null;
			private Incollection incollection_buffer = null;
			private Book book_buffer = null;
			private Inproceedings inproceedings_buffer = null;

			public void startElement(String uri, String localName, String element_name, Attributes attributes) {

				DblpElement element = DblpElement.getDblpElement(element_name);

				if (element != null) {

					String key = attributes.getValue("key");

					switch (element) {
					case WWW:
						if (key.contains("homepages")) { // This tag contains
															// author
															// info
							isWWWElement = true;
							author_buffer = new Person();
						}
						break;
					case ARTICLE:
						publication_buffer = new Publication();
						publication_buffer.setPubkey(key);
						publication_buffer.setPubltype(PublicationType.ARTICLE);
						article_buffer = new Article(publication_buffer.getPubid());

						isArticleElement = true;
						break;
					case INCOLLECTION:
						publication_buffer = new Publication();
						publication_buffer.setPubkey(key);
						publication_buffer.setPubltype(PublicationType.INCOLLECTION);
						incollection_buffer = new Incollection(publication_buffer.getPubid());

						isIncollectionElement = true;
						break;
					case BOOK:
						publication_buffer = new Publication();
						publication_buffer.setPubkey(key);
						publication_buffer.setPubltype(PublicationType.BOOK);
						book_buffer = new Book(publication_buffer.getPubid());

						isBookElement = true;
						break;
					case INPROCEEDINGS:
						publication_buffer = new Publication();
						publication_buffer.setPubkey(key);
						publication_buffer.setPubltype(PublicationType.INPROCEEDINGS);
						inproceedings_buffer = new Inproceedings(publication_buffer.getPubid());

						isInproceedingsElement = true;
						break;
					default:
						break;
					}
				} else {

					if (isWWWElement) {
						if (element_name.equals("author")) {
							attribute_value_buffer = new ArrayList<String>();
							isWWW_author = true;
						} else if (element_name.equals("title")) {
							attribute_value_buffer = new ArrayList<String>();
							isWWW_title = true;
						}
					} else if (isArticleElement) {
						if (element_name.equals("author")) {
							attribute_value_buffer = new ArrayList<String>();
							isArticle_author = true;
						} else if (element_name.equals("title")) {
							attribute_value_buffer = new ArrayList<String>();
							isArticle_title = true;
						} else if (element_name.equals("year")) {
							attribute_value_buffer = new ArrayList<String>();
							isArticle_year = true;
						} else if (element_name.equals("pages")) {
							attribute_value_buffer = new ArrayList<String>();
							isArticle_pages = true;
						} else if (element_name.equals("volume")) {
							attribute_value_buffer = new ArrayList<String>();
							isArticle_volume = true;
						} else if (element_name.equals("journal")) {
							attribute_value_buffer = new ArrayList<String>();
							isArticle_journal = true;
						} else if (element_name.equals("number")) {
							attribute_value_buffer = new ArrayList<String>();
							isArticle_number = true;
						} else if (element_name.equals("month")) {
							attribute_value_buffer = new ArrayList<String>();
							isArticle_month = true;
						}
					} else if (isIncollectionElement) {
						if (element_name.equals("author")) {
							attribute_value_buffer = new ArrayList<String>();
							isIncollection_author = true;
						} else if (element_name.equals("title")) {
							attribute_value_buffer = new ArrayList<String>();
							isIncollection_title = true;
						} else if (element_name.equals("year")) {
							attribute_value_buffer = new ArrayList<String>();
							isIncollection_year = true;
						} else if (element_name.equals("pages")) {
							attribute_value_buffer = new ArrayList<String>();
							isIncollection_pages = true;
						} else if (element_name.equals("isbn")) {
							attribute_value_buffer = new ArrayList<String>();
							isIncollection_isbn = true;
						} else if (element_name.equals("booktitle")) {
							attribute_value_buffer = new ArrayList<String>();
							isIncollection_booktitle = true;
						}
					} else if (isBookElement) {
						if (element_name.equals("author")) {
							attribute_value_buffer = new ArrayList<String>();
							isBook_author = true;
						} else if (element_name.equals("title")) {
							attribute_value_buffer = new ArrayList<String>();
							isBook_title = true;
						} else if (element_name.equals("year")) {
							attribute_value_buffer = new ArrayList<String>();
							isBook_year = true;
						} else if (element_name.equals("pages")) {
							attribute_value_buffer = new ArrayList<String>();
							isBook_pages = true;
						} else if (element_name.equals("isbn")) {
							attribute_value_buffer = new ArrayList<String>();
							isBook_isbn = true;
						} else if (element_name.equals("editor")) {
							attribute_value_buffer = new ArrayList<String>();
							isBook_editor = true;
						} else if (element_name.equals("publisher")) {
							attribute_value_buffer = new ArrayList<String>();
							isBook_publisher = true;
						}
					} else if (isInproceedingsElement) {
						if (element_name.equals("author")) {
							attribute_value_buffer = new ArrayList<String>();
							isInproceedings_author = true;
						} else if (element_name.equals("title")) {
							attribute_value_buffer = new ArrayList<String>();
							isInproceedings_title = true;
						} else if (element_name.equals("year")) {
							attribute_value_buffer = new ArrayList<String>();
							isInproceedings_year = true;
						} else if (element_name.equals("pages")) {
							attribute_value_buffer = new ArrayList<String>();
							isInproceedings_pages = true;
						} else if (element_name.equals("booktitle")) {
							attribute_value_buffer = new ArrayList<String>();
							isInproceedings_booktitle = true;
						} else if (element_name.equals("editor")) {
							attribute_value_buffer = new ArrayList<String>();
							isInproceedings_editor = true;
						} else if (element_name.equals("publisher")) {
							attribute_value_buffer = new ArrayList<String>();
							isInproceedings_publisher = true;
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
							if(!author_buffer.getNames().isEmpty()) {
								persons.add(author_buffer);
							}
							author_buffer = null;

							isWWWElement = false;
						}
						break;
					case ARTICLE:
						if (isArticleElement) {
							publications.add(publication_buffer);
							articles.add(article_buffer);
							article_buffer = null;
							publication_buffer = null;

							isArticleElement = false;
						}
						break;
					case INCOLLECTION:
						if (isIncollectionElement) {
							publications.add(publication_buffer);
							incollections.add(incollection_buffer);
							incollection_buffer = null;
							publication_buffer = null;

							isIncollectionElement = false;
						}
						break;
					case BOOK:
						if (isBookElement) {
							publications.add(publication_buffer);
							books.add(book_buffer);
							book_buffer = null;
							publication_buffer = null;

							isBookElement = false;
						}
						break;
					case INPROCEEDINGS:
						if (isInproceedingsElement) {
							publications.add(publication_buffer);
							inproceedingsList.add(inproceedings_buffer);
							inproceedings_buffer = null;
							publication_buffer = null;

							isInproceedingsElement = false;
						}
						break;
					default:
						break;
					}
				} else {
					if (isWWWElement) {
						String value = StringUtils.join(attribute_value_buffer, "");

						if (element_name.equals("author")) {
							author_buffer.addName(value);
							isWWW_author = false;
						} else if (element_name.equals("title")) {
							author_buffer.setTitle(value);
							isWWW_title = false;
						}

					} else if (isArticleElement) {
						String value = StringUtils.join(attribute_value_buffer, "");

						if (element_name.equals("author")) {
							publication_buffer.addAuthor(value);
							isArticle_author = false;
						} else if (element_name.equals("title")) {
							publication_buffer.setTitle(value);
							isArticle_title = false;
						} else if (element_name.equals("year")) {
							publication_buffer.setYear(Integer.parseInt(value));
							isArticle_year = false;
						} else if (element_name.equals("pages")) {
							publication_buffer.setPages(value);
							isArticle_pages = false;
						} else if (element_name.equals("volume")) {
							article_buffer.setVolume(value);
							isArticle_volume = false;
						} else if (element_name.equals("journal")) {
							article_buffer.setJournal(value);
							isArticle_journal = false;
						} else if (element_name.equals("number")) {
							article_buffer.setNumber(value);
							isArticle_number = false;
						} else if (element_name.equals("month")) {
							article_buffer.setMonth(value);
							isArticle_month = false;
						}
					} else if (isIncollectionElement) {
						String value = StringUtils.join(attribute_value_buffer, "");

						if (element_name.equals("author")) {
							publication_buffer.addAuthor(value);
							isIncollection_author = false;
						} else if (element_name.equals("title")) {
							publication_buffer.setTitle(value);
							isIncollection_title = false;
						} else if (element_name.equals("year")) {
							publication_buffer.setYear(Integer.parseInt(value));
							isIncollection_year = false;
						} else if (element_name.equals("pages")) {
							publication_buffer.setPages(value);
							isIncollection_pages = false;
						} else if (element_name.equals("isbn")) {
							incollection_buffer.setIsbn(value);
							isIncollection_isbn = false;
						} else if (element_name.equals("booktitle")) {
							incollection_buffer.setBooktitle(value);
							isIncollection_booktitle = false;
						}
					} else if (isBookElement) {
						String value = StringUtils.join(attribute_value_buffer, "");

						if (element_name.equals("author")) {
							publication_buffer.addAuthor(value);
							isBook_author = false;
						} else if (element_name.equals("title")) {
							publication_buffer.setTitle(value);
							isBook_title = false;
						} else if (element_name.equals("year")) {
							publication_buffer.setYear(Integer.parseInt(value));
							isBook_year = false;
						} else if (element_name.equals("pages")) {
							publication_buffer.setPages(value);
							isBook_pages = false;
						} else if (element_name.equals("isbn")) {
							book_buffer.setIsbn(value);
							isBook_isbn = false;
						} else if (element_name.equals("editor")) {
							publication_buffer.addEditor(value);
							isBook_editor = false;
						} else if (element_name.equals("publisher")) {
							Integer publisher_id = publisher_id_map.get(value);

							if (publisher_id == null) {
								Publisher publisher = new Publisher(value);
								publisher_id = publisher.getId();
								publisher_id_map.put(publisher.getName(), publisher_id);
								publishers.add(publisher);
							}

							publication_buffer.setPublisherId(publisher_id);
							isBook_publisher = false;
						}
					} else if (isInproceedingsElement) {
						String value = StringUtils.join(attribute_value_buffer, "");

						if (element_name.equals("author")) {
							publication_buffer.addAuthor(value);
							isInproceedings_author = false;
						} else if (element_name.equals("title")) {
							publication_buffer.setTitle(value);
							isInproceedings_title = false;
						} else if (element_name.equals("year")) {
							publication_buffer.setYear(Integer.parseInt(value));
							isInproceedings_year = false;
						} else if (element_name.equals("pages")) {
							publication_buffer.setPages(value);
							isInproceedings_pages = false;
						} else if (element_name.equals("booktitle")) {
							inproceedings_buffer.setBooktitle(value);
							isInproceedings_booktitle = false;
						} else if (element_name.equals("editor")) {
							publication_buffer.addEditor(value);
							isInproceedings_editor = false;
						} else if (element_name.equals("publisher")) {
							Integer publisher_id = publisher_id_map.get(value);

							if (publisher_id == null) {
								Publisher publisher = new Publisher(value);
								publisher_id = publisher.getId();
								publisher_id_map.put(publisher.getName(), publisher_id);
								publishers.add(publisher);
							}

							publication_buffer.setPublisherId(publisher_id);
							isInproceedings_publisher = false;
						}
					}
				}

			}

			public void characters(char ch[], int start, int length) throws SAXException {
				String value = new String(ch, start, length);

				if (isWWWElement) { // WWW element
					if (isWWW_author | isWWW_title) {
						attribute_value_buffer.add(value);
					}
				} else if (isArticleElement) { // Article Element
					if (isArticle_author | isArticle_title | isArticle_pages | isArticle_year | isArticle_volume
							| isArticle_journal | isArticle_number | isArticle_month) {
						attribute_value_buffer.add(value);
					}
				} else if (isIncollectionElement) {
					if (isIncollection_author | isIncollection_title | isIncollection_pages | isIncollection_year
							| isIncollection_isbn | isIncollection_booktitle) {
						attribute_value_buffer.add(value);
					}
				} else if (isBookElement) {
					if (isBookElement | isBook_author | isBook_title | isBook_pages | isBook_year | isBook_isbn
							| isBook_publisher | isBook_editor) {
						attribute_value_buffer.add(value);
					}
				} else if (isInproceedingsElement) {
					if (isInproceedingsElement | isInproceedings_author | isInproceedings_title | isInproceedings_pages
							| isInproceedings_year | isInproceedings_booktitle | isInproceedings_publisher
							| isInproceedings_editor) {
						attribute_value_buffer.add(value);
					}
				}
			}

			public void endDocument() throws SAXException {

				// Prepare person name by using person data
				for (Person person : persons) {
					for (String name : person.getNames()) {
						PersonName personName = new PersonName(person.getId(), name);
						personNameList.add(personName);
						
						if(person.getPrimaryname_id() == null) {
							person.setPrimaryname_id(personName.getPnId());
						}
						
						// Build the mapping of name and id.
						ArrayList<Integer> id_list = person_name_id_mapping.get(name);
						if(id_list == null) {
							id_list = new ArrayList<Integer>();
						}
						
						id_list.add(person.getId());
						person_name_id_mapping.put(name, id_list);
					}
				}
				
				// Build the entry for PublicationEditor and PublicationAuthor table				
				for(Publication publication: publications) {
					
					for(String author_name : publication.getAuthors()) {
						
						ArrayList<Integer> id_list = person_name_id_mapping.get(author_name);
						if(id_list != null) {
							PublicationAuthor publicationAuthor = new PublicationAuthor(id_list.get(0), publication.getPubid());
							publicationAuthors.add(publicationAuthor);
						}
						else {
							System.out.println("Author not found");
						}
						
					}
					
					for(String editor_name : publication.getEditors()) {
						
						ArrayList<Integer> id_list = person_name_id_mapping.get(editor_name);
						if(id_list != null) {
							PublicationEditor publicationEditor = new PublicationEditor(id_list.get(0), publication.getPubid());						
							publicationEditors.add(publicationEditor);
						}
						else {
							System.out.println("Editor not found");
						}
					}
					
					
				}

				
				System.out.println("Writing to CSV files...");
				// Generate CSV files
				System.out.println("Writing Person.csv");
				generatePersonCSVFile();
				
				System.out.println("Writing PersoneName.csv");
				generatePersonNameCSVFile();
				
				System.out.println("Writing Publication.csv");
				generatePublicationCSVFile();
				
				System.out.println("Writing Article.csv");
				generateArticleCSVFile();
				
				System.out.println("Writing Incollection.csv");
				generateIncollectionCSCVFile();
				
				System.out.println("Writing Book.csv");
				generateBookCSVFile();
				
				System.out.println("Writing Inproceedings.csv");
				generateInproceedingsCSVFile();
				
				System.out.println("Writing Publisher.csv");
				generatePublisherCSVFile();
				
				System.out.println("Writing PublicationAuthor.csv");
				generatePublicationAuthorCSVFile();
				
				System.out.println("Writing PublicationEditor.csv");
				generatePublicationEditorCSVFile();
			}

			/*
			 * Functions to generate csv file
			 */

			public void generatePersonNameCSVFile() {

				try {

					File personNameCSVFile = new File("target/PersonName.csv");

					if (personNameCSVFile.exists()) {
						personNameCSVFile.delete();
					} else {
						personNameCSVFile.createNewFile();
					}

					BufferedWriter bw = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(personNameCSVFile), "UTF-8"));

					bw.write(PersonName.getColumnNameList());

					for (PersonName personName : personNameList) {
						bw.write(personName.toString());
					}

					bw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			public void generatePersonCSVFile() {

				try {

					File personCSVFile = new File("target/Person.csv");

					if (personCSVFile.exists()) {
						personCSVFile.delete();
					} else {
						personCSVFile.createNewFile();
					}

					BufferedWriter bw = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(personCSVFile), "UTF-8"));

					bw.write(Person.getColumnNameList());

					for (Person person : persons) {
						bw.write(person.toString());
					}

					bw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			public void generatePublicationCSVFile() {

				try {

					File publicationCSVFile = new File("target/Publication.csv");

					if (publicationCSVFile.exists()) {
						publicationCSVFile.delete();
					} else {
						publicationCSVFile.createNewFile();
					}

					BufferedWriter bw = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(publicationCSVFile), "UTF-8"));

					bw.write(Publication.getColumnNameList());

					for (Publication publication : publications) {
						bw.write(publication.toString());
					}

					bw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			public void generateArticleCSVFile() {

				try {

					File articleCSVFile = new File("target/Article.csv");

					if (articleCSVFile.exists()) {
						articleCSVFile.delete();
					} else {
						articleCSVFile.createNewFile();
					}

					BufferedWriter bw = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(articleCSVFile), "UTF-8"));

					bw.write(Article.getColumnNameList());

					for (Article article : articles) {
						bw.write(article.toString());
					}

					bw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			private void generateIncollectionCSCVFile() {

				try {

					File incollectionCSVFile = new File("target/Incollection.csv");

					if (incollectionCSVFile.exists()) {
						incollectionCSVFile.delete();
					} else {
						incollectionCSVFile.createNewFile();
					}

					BufferedWriter bw = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(incollectionCSVFile), "UTF-8"));

					bw.write(Incollection.getColumnNameList());

					for (Incollection incollection : incollections) {
						bw.write(incollection.toString());
					}

					bw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			public void generateBookCSVFile() {

				try {

					File bookCSVFile = new File("target/Book.csv");

					if (bookCSVFile.exists()) {
						bookCSVFile.delete();
					} else {
						bookCSVFile.createNewFile();
					}

					BufferedWriter bw = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(bookCSVFile), "UTF-8"));

					bw.write(Book.getColumnNameList());

					for (Book book : books) {
						bw.write(book.toString());
					}

					bw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			public void generatePublisherCSVFile() {
				try {

					File publisherCSVFile = new File("target/Publisher.csv");

					if (publisherCSVFile.exists()) {
						publisherCSVFile.delete();
					} else {
						publisherCSVFile.createNewFile();
					}

					BufferedWriter bw = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(publisherCSVFile), "UTF-8"));

					bw.write(Publisher.getColumnNameList());

					for (Publisher publisher : publishers) {
						bw.write(publisher.toString());
					}

					bw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			private void generateInproceedingsCSVFile() {
				try {

					File inproceedingsCSVFile = new File("target/Inproceedings.csv");

					if (inproceedingsCSVFile.exists()) {
						inproceedingsCSVFile.delete();
					} else {
						inproceedingsCSVFile.createNewFile();
					}

					BufferedWriter bw = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(inproceedingsCSVFile), "UTF-8"));

					bw.write(Inproceedings.getColumnNameList());

					for (Inproceedings inproceedings : inproceedingsList) {
						bw.write(inproceedings.toString());
					}

					bw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			private void generatePublicationAuthorCSVFile() {
				
				try {

					File publicationAuthorCSVFile = new File("target/PublicationAuthor.csv");

					if (publicationAuthorCSVFile.exists()) {
						publicationAuthorCSVFile.delete();
					} else {
						publicationAuthorCSVFile.createNewFile();
					}

					BufferedWriter bw = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(publicationAuthorCSVFile), "UTF-8"));

					bw.write(PublicationAuthor.getColumnNameList());

					for (PublicationAuthor publicationAuthor : publicationAuthors) {
						bw.write(publicationAuthor.toString());
					}

					bw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			private void generatePublicationEditorCSVFile() {
				
				try {

					File publicationEditorCSVFile = new File("target/PublicationEditor.csv");

					if (publicationEditorCSVFile.exists()) {
						publicationEditorCSVFile.delete();
					} else {
						publicationEditorCSVFile.createNewFile();
					}

					BufferedWriter bw = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(publicationEditorCSVFile), "UTF-8"));

					bw.write(PublicationEditor.getColumnNameList());

					for (PublicationEditor publicationEditor : publicationEditors) {
						bw.write(publicationEditor.toString());
					}

					bw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		};

		File file = new File("src/main/resources/dblp.xml");
		InputStream inputStream = new FileInputStream(file);
		Reader reader = new InputStreamReader(inputStream, "UTF-8");

		InputSource is = new InputSource(reader);
		is.setEncoding("UTF-8");
		
		System.out.println("Processing Started...");
		parser.parse(is, handler);
	}

}
