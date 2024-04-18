package com.libapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.libapp.model.Document;

public class DocumentDao {
	final String url = "jdbc:postgresql://localhost:5432/library?allowMultiQueries=true";
	final String user = "postgres";
	final String password = "root";

	public Document[] getDocument(String title) throws SQLException {
        String getDoc = "select * from documenttype natural join documents inner join details on documents.did=details.did and (lower(documents.title) like ? or lower(documents.author) like ? or lower(documents.genre) like ? or lower(documenttype.doctype) like ?) natural join record";

        Document doc[];
        
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Connection conn = DriverManager.getConnection(url, user, password);
		PreparedStatement pStmt = conn.prepareStatement(getDoc);

	 	pStmt.setString(1, "%" + title.trim().toLowerCase() + "%");
	 	pStmt.setString(2, "%" + title.trim().toLowerCase() + "%");
	 	pStmt.setString(3, "%" + title.trim().toLowerCase() + "%");
	 	pStmt.setString(4, "%" + title.trim().toLowerCase() + "%");
	 		
//	 	System.out.println("-----------" + getDoc);
	 		
        ResultSet rs = pStmt.executeQuery();
        
        int count = 0;
        String getCount = "select count(*) from documenttype natural join documents inner join details on documents.did=details.did and (lower(documents.title) like ? or lower(documents.author) like ? or lower(documents.genre) like ? or lower(documenttype.doctype) like ?) natural join record";
        PreparedStatement pStmt1 = conn.prepareStatement(getCount);
        pStmt1.setString(1, "%" + title.trim().toLowerCase() + "%");
	 	pStmt1.setString(2, "%" + title.trim().toLowerCase() + "%");
	 	pStmt1.setString(3, "%" + title.trim().toLowerCase() + "%");
	 	pStmt1.setString(4, "%" + title.trim().toLowerCase() + "%");
        ResultSet rs1 = pStmt1.executeQuery();
        if(rs1.next()) {
        	count = rs1.getInt("count");
        }
        
        doc = new Document[count];
            
        for(int i = 0; rs.next(); i++) {
            /* Retrieves the value of the designated column in the current row 
            	of this ResultSet object as a String in the Java programming language.
            */
        	doc[i] = new Document();
        	
            doc[i].setDid(rs.getInt("did"));
            doc[i].setTitle(rs.getString("title")); 
            doc[i].setAuthor(rs.getString("author")); 
            doc[i].setGenre(rs.getString("genre")); 
            doc[i].setPublisher(rs.getString("publisher"));
            doc[i].setPublishYear(rs.getInt("publishyear"));
            doc[i].setEdition(rs.getInt("edition"));
            doc[i].setIssueNo(rs.getInt("issueno"));
            doc[i].setIssueDate(rs.getDate("issuedate"));
            doc[i].setEditor(rs.getString("editor"));
            doc[i].setDocName(rs.getString("docname"));
            doc[i].setDtid(rs.getInt("dtid"));
            doc[i].setDocType(rs.getString("doctype"));
            doc[i].setRecid(rs.getInt("recid"));
            doc[i].setTotal(rs.getInt("total"));
            doc[i].setIssued(rs.getInt("issued"));
            doc[i].setRemaining(rs.getInt("remaining"));
        }
		
//		System.out.println("----------------" + doc);
		return doc;
	
	}
	
	public void updateDocRecord(Integer did, String updateType) {
		String updateDocRecord = "update record set issued = issued where recid in(select record.recid from documents, details, record where documents.did=details.did and details.recid=record.recid and documents.did = ?);";
		if(updateType.equals("b")) {
			updateDocRecord = "update record set issued = issued + 1 where recid in(select record.recid from documents, details, record where documents.did=details.did and details.recid=record.recid and documents.did = ?);";
		} else if(updateType.equals("r")) {
			updateDocRecord = "update record set issued = issued - 1 where recid in(select record.recid from documents, details, record where documents.did=details.did and details.recid=record.recid and documents.did = ?);";
		} else if(updateType.equals("a")) {
			updateDocRecord = "update record set total = total + 1 where recid in(select record.recid from documents, details, record where documents.did=details.did and details.recid=record.recid and documents.did = ?);";
		} else if(updateType.equals("d")) {
			updateDocRecord = "update record set total = total - 1 where recid in(select record.recid from documents, details, record where documents.did=details.did and details.recid=record.recid and documents.did = ?);";
		}
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (Connection conn = DriverManager.getConnection(url, user, password);
	 		    PreparedStatement updateMemStmt = conn.prepareStatement(updateDocRecord)){
	 			
			updateMemStmt.setInt(1, did);
            boolean updated = updateMemStmt.execute();
       } catch (SQLException e) {
    		 e.printStackTrace();;
       }
	}
	
	public void deleteDoc(Integer did) {
		String getRecid = "select recid from details where did = ?";
		
		String deleteDoc = "delete from details where did = ?;"
						 + "delete from record where recid = ?;"
						 + "delete from documents where did = ?";
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (Connection conn = DriverManager.getConnection(url, user, password);
	 		    PreparedStatement updateMemStmt = conn.prepareStatement(deleteDoc);
				PreparedStatement getRecidStmt = conn.prepareStatement(getRecid)){
	 			
			getRecidStmt.setInt(1, did);
			
			ResultSet rs = getRecidStmt.executeQuery();
			Integer recid = 0;
			while(rs.next()) {
				recid = rs.getInt("recid");
			}
			
			updateMemStmt.setInt(1, did);
			updateMemStmt.setInt(2, recid);
			updateMemStmt.setInt(3, did);
			
            boolean deleted = updateMemStmt.execute();
       } catch (SQLException e) {
    		 e.printStackTrace();;
       }
	}
	
	public void updateDoc(Document doc, Integer lid) {
		String getDtid = "select dtid from documenttype where doctype = ?";
		
		String updateDoc = "update documents set title = ? where did = ?;"
						 + "update documents set publisher = ? where did = ?;"
						 + "update documents set publishyear = ? where did = ?;"
						 + "update documents set edition = ? where did = ?;"
						 + "update documents set issueno = ? where did = ?;"
						 + "update documents set issuedate = ? where did = ?;"
						 + "update documents set editor = ? where did = ?;"
						 + "update documents set docname = ? where did = ?;"
						 + "update documents set dtid = ? where did = ?;"
						 + "update documents set author = ? where did = ?;"
						 + "update documents set genre = ? where did = ?;"
						 + "update documents set lid = ? where did = ?;";
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (Connection conn = DriverManager.getConnection(url, user, password);
	 		    PreparedStatement updateDocStmt = conn.prepareStatement(updateDoc);
				PreparedStatement getDtidStmt = conn.prepareStatement(getDtid)){
	 			
			getDtidStmt.setString(1, doc.getDocType());
			System.out.println("dsfdgfdfgh  " + doc.getPublishYear());
			ResultSet rs = getDtidStmt.executeQuery();
			while(rs.next()) {
				doc.setDtid(rs.getInt("dtid"));
			}
			
			updateDocStmt.setString(1, doc.getTitle());updateDocStmt.setInt(2, doc.getDid());
			updateDocStmt.setString(3, doc.getPublisher());updateDocStmt.setInt(4, doc.getDid());
			updateDocStmt.setInt(5, doc.getPublishYear());updateDocStmt.setInt(6, doc.getDid());
			updateDocStmt.setInt(7, doc.getEdition());updateDocStmt.setInt(8, doc.getDid());
			
			if(doc.getIssueNo() != null) {
				updateDocStmt.setInt(9, doc.getIssueNo());
			} else {
				updateDocStmt.setNull(9, Types.INTEGER);
			}
			
			if(doc.getIssueDate() != null) {
				updateDocStmt.setDate(11, doc.getIssueDate());
			} else {
				updateDocStmt.setNull(11, Types.DATE);
			}
			
			if(doc.getEditor() != null) {
				updateDocStmt.setString(13, doc.getEditor());
			} else {
				updateDocStmt.setNull(13, Types.VARCHAR);
			}
			
			if(doc.getDocName() != null) {
				updateDocStmt.setString(15, doc.getDocName());
			} else {
				updateDocStmt.setNull(15, Types.VARCHAR);
			}
			
			updateDocStmt.setInt(10, doc.getDid());
			updateDocStmt.setInt(12, doc.getDid());
			updateDocStmt.setInt(14, doc.getDid());
			updateDocStmt.setInt(16, doc.getDid());
			
			updateDocStmt.setInt(17, doc.getDtid());updateDocStmt.setInt(18, doc.getDid());
			updateDocStmt.setString(19, doc.getAuthor());updateDocStmt.setInt(20, doc.getDid());
			updateDocStmt.setString(21, doc.getGenre());updateDocStmt.setInt(22, doc.getDid());
			updateDocStmt.setInt(23, lid);updateDocStmt.setInt(24, doc.getDid());
			
            boolean updated = updateDocStmt.execute();
       } catch (SQLException e) {
    		 e.printStackTrace();;
       }
	}
	
	public void addDoc(Document doc, Integer lid) {
		String getDtid = "select dtid from documenttype where doctype = ?";
		String getMaxRecid = "select recid from record order by recid desc limit 1";
		String getMaxDid = "select did from documents order by did desc limit 1";
		
		String addDoc = "insert into documents values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
					  + "insert into record values (?, ?, ?);"
					  + "insert into details values (?, ?)";
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (Connection conn = DriverManager.getConnection(url, user, password);
	 		    PreparedStatement addDocStmt = conn.prepareStatement(addDoc);
				PreparedStatement getDtidStmt = conn.prepareStatement(getDtid);
				PreparedStatement getMaxRecidStmt = conn.prepareStatement(getMaxRecid);
				PreparedStatement getMaxDidStmt = conn.prepareStatement(getMaxDid)){
	 			
			getDtidStmt.setString(1, doc.getDocType());
			
			ResultSet rs = getDtidStmt.executeQuery();
			while(rs.next()) {
				doc.setDtid(rs.getInt("dtid"));
			}
			
			ResultSet rs1 = getMaxRecidStmt.executeQuery();
			int recid = 0;
			while(rs1.next()) {
				recid = rs1.getInt("recid") + 1;
			}
			
			ResultSet rs2 = getMaxDidStmt.executeQuery();
			while(rs2.next()) {
				doc.setDid(rs2.getInt("did") + 1);
			}
			
			addDocStmt.setInt(1, doc.getDid());
			addDocStmt.setString(2, doc.getTitle());
			addDocStmt.setString(3, doc.getPublisher());
			addDocStmt.setInt(4, doc.getPublishYear());
			addDocStmt.setInt(5, doc.getEdition());
			if(doc.getIssueNo() != null) {
				addDocStmt.setInt(6, doc.getIssueNo());
			} else {
				addDocStmt.setNull(6, Types.INTEGER);
			}
			
			if(doc.getIssueDate() != null) {
				addDocStmt.setDate(7, doc.getIssueDate());
			} else {
				addDocStmt.setNull(7, Types.DATE);
			}
			
			if(doc.getEditor() != null) {
				addDocStmt.setString(8, doc.getEditor());
			} else {
				addDocStmt.setNull(8, Types.VARCHAR);
			}
			
			if(doc.getDocName() != null) {
				addDocStmt.setString(9, doc.getDocName());
			} else {
				addDocStmt.setNull(9, Types.VARCHAR);
			}

			addDocStmt.setInt(10, doc.getDtid());
			addDocStmt.setString(11, doc.getAuthor());
			addDocStmt.setString(12, doc.getGenre());
			addDocStmt.setInt(13, lid);
			addDocStmt.setInt(14, recid);
			addDocStmt.setInt(15, doc.getTotal());
			addDocStmt.setInt(16, 0);
			addDocStmt.setInt(17, recid);
			addDocStmt.setInt(18, doc.getDid());
			
			
            boolean updated = addDocStmt.execute();
       } catch (SQLException e) {
    		 e.printStackTrace();;
       }
	}
}
