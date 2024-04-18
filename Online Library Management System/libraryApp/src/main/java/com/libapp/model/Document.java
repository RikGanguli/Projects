package com.libapp.model;

import java.sql.Date;

public class Document {
	private Integer did;
	private String title;
	public String author;
	public String genre;
	private String publisher;
	private Integer publishYear;
	private Integer edition;
	private Integer issueNo;
	private Date issueDate;
	private String editor;
	private String docName;
	private Integer dtid;
	private String docType;
	private Integer recid;
	private Integer total;
	private Integer issued;
	private Integer remaining;
	private Integer lid;

	public Document() {
			
	}
	
	public Document(String docData) {
		String[] doc = docData.split("\\|+");
//		System.out.println(doc.length);
//		System.out.println(doc[5]);
		
		this.did = Integer.parseInt(doc[0]);
		this.title = !doc[1].equals("null") ? doc[1] : null;
		this.publisher = !doc[2].equals("null") ? doc[2] : null;
		this.publishYear = !doc[3].equals("null") ? Integer.parseInt(doc[3]) : null;
		this.edition = !doc[4].equals("null") ? Integer.parseInt(doc[4]) : null;
		this.issueNo = !doc[5].equals("null") ? Integer.parseInt(doc[5]) : null;
		this.issueDate = !doc[6].equals("null") ? Date.valueOf(doc[6]) : null;
		this.editor = !doc[7].equals("null") ? doc[7] : null;
		this.docName = !doc[8].equals("null") ? doc[8] : null;
		this.dtid = !doc[9].equals("null") ? Integer.parseInt(doc[9]) : null;
		this.docType = !doc[10].equals("null") ? doc[10] : null;
		this.recid = !doc[11].equals("null") ? Integer.parseInt(doc[11]) : null;
		this.total = !doc[12].equals("null") ? Integer.parseInt(doc[12]) : null;
		this.issued = !doc[13].equals("null") ? Integer.parseInt(doc[13]) : null;
		this.remaining = !doc[14].equals("null") ? Integer.parseInt(doc[14]) : null;
		this.author = !doc[15].equals("null") ? doc[15] : null;
		this.genre = !doc[15].equals("null") ? doc[16] : null;
	}
	
	@Override
	public String toString() {
		return did + "|" + title + "|" + publisher + "|" + publishYear
				+ "|" + edition + "|" + issueNo + "|" + issueDate + "|" + editor
				+ "|" + docName + "|" + dtid + "|" + docType + "|" + recid + "|"
				+ total + "|" + issued + "|" + remaining + "|" + author + "|" + genre + "|" + lid;
	}
	
	public Integer getLid() {
		return lid;
	}

	public void setLid(Integer lid) {
		this.lid = lid;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Integer getRecid() {
		return recid;
	}

	public void setRecid(Integer recid) {
		this.recid = recid;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getIssued() {
		return issued;
	}

	public void setIssued(Integer issued) {
		this.issued = issued;
	}

	public Integer getRemaining() {
		return remaining;
	}

	public void setRemaining(Integer remaining) {
		this.remaining = remaining;
	}
	
	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}
	
	public int getDid() {
		return did;
	}
	public void setDid(int did) {
		this.did = did;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public Integer getPublishYear() {
		return publishYear;
	}
	public void setPublishYear(Integer publishYear) {
		this.publishYear = publishYear;
	}
	public Integer getEdition() {
		return edition;
	}
	public void setEdition(Integer edition) {
		this.edition = edition;
	}
	public Integer getIssueNo() {
		return issueNo;
	}
	public void setIssueNo(Integer issueNo) {
		this.issueNo = issueNo;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public int getDtid() {
		return dtid;
	}
	public void setDtid(int dtid) {
		this.dtid = dtid;
	}
}
