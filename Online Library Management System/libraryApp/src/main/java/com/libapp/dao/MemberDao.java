package com.libapp.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.libapp.model.Member;

public class MemberDao {
	final String url = "jdbc:postgresql://localhost:5432/library?allowMultiQueries=true";
	final String user = "postgres";
	final String password = "root";

	public Member getMemberData(Integer mid) {
		String memData = "select * from members inner join membership using (mid) where mid = ?";
        
        Member mem = new Member();

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (Connection conn = DriverManager.getConnection(url, user, password);
	 		    PreparedStatement memStmt = conn.prepareStatement(memData)){
	 			
			memStmt.setInt(1, mid);
	 		
            ResultSet memRes = memStmt.executeQuery();
            
            //If account exists
            while (memRes.next()) {
            	mem.setMid(memRes.getInt("mid"));
            	mem.setLastname(memRes.getString("lastname"));
            	mem.setFirstname(memRes.getString("firstname"));
            	mem.setAddress(memRes.getString("address"));
            	mem.setContact(memRes.getInt("contact"));
            	mem.setDob(memRes.getDate("dob"));
            	mem.setEmail(memRes.getString("email"));
            	mem.setDueDate(memRes.getDate("duedate"));
            	mem.setReturnDate(memRes.getDate("returndate"));
            	mem.setDid(memRes.getInt("did"));
            	mem.setOnTime(memRes.getBoolean("ontime"));
            	mem.setDoj(memRes.getDate("doj"));
            	mem.setBorrowCount(memRes.getInt("borrowcount"));
            	mem.setLatefees(memRes.getInt("latefees"));
            }
       }catch (SQLException e) {
    		 System.out.println(e.getMessage());
       }
		
		return mem;
	}
	
	public Member updateMember(Member member, Integer did, String updateType) {
		Member mem = new Member();
		
		if(updateType.equals("b")) {
			String updateMem = "update members set did = ? where mid = ?;"
							+"update members set duedate = ? where mid = ?;"
							+"update members set returndate = null where mid = ?;"
							+"update members set ontime = null where mid = ?;"
							+"update membership set borrowcount = borrowcount + 1 where mid = ?";
	
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try (Connection conn = DriverManager.getConnection(url, user, password);
		 		    PreparedStatement updateMemStmt = conn.prepareStatement(updateMem)){
				
				updateMemStmt.setInt(1, did);
				updateMemStmt.setInt(2, member.getMid());
				updateMemStmt.setDate(3, Date.valueOf(LocalDate.now().plusDays(7)));
				updateMemStmt.setInt(4, member.getMid());
				updateMemStmt.setInt(5, member.getMid());
				updateMemStmt.setInt(6, member.getMid());
				updateMemStmt.setInt(7, member.getMid());
		 		
	            boolean updated = updateMemStmt.execute();
	       }catch (SQLException e) {
	    		 System.out.println(e.getMessage());
	       }
		} else if(updateType.equals("r")) {
			boolean onTime = Date.valueOf(LocalDate.now()).after(member.getDueDate()) ? false : true;
			int lateFees = !onTime ? Date.valueOf(LocalDate.now()).compareTo(member.getDueDate()) : 0;
			
			String updateMem = "update members set returndate = ? where mid = ?;"
					+"update members set ontime = ? where mid = ?;"
					+"update membership set latefees = latefees + ? where mid = ?;";

			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try (Connection conn = DriverManager.getConnection(url, user, password);
		 		    PreparedStatement updateMemStmt = conn.prepareStatement(updateMem)){
				
				updateMemStmt.setDate(1, Date.valueOf(LocalDate.now()));
				updateMemStmt.setInt(2, member.getMid());
				updateMemStmt.setBoolean(3, onTime);
				updateMemStmt.setInt(4, member.getMid());
				updateMemStmt.setInt(5, lateFees);
				updateMemStmt.setInt(6, member.getMid());
		 		
		        boolean updated = updateMemStmt.execute();
		   }catch (SQLException e) {
				 System.out.println(e.getMessage());
		   }
		}
		
		mem = getMemberData(member.getMid());
		return mem;
	}
}
