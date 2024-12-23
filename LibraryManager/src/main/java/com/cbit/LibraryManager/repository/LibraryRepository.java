package com.cbit.LibraryManager.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.cbit.LibraryManager.model.Book;
import com.cbit.LibraryManager.model.IssueBook;
import com.cbit.LibraryManager.model.SearchBook;

@Repository
public class LibraryRepository {

	public int addBookRepository(Book book) throws Exception {
		int i;
		try {
			System.out.println("inside repository");
			System.out.println(book);

			Connection con = getConnection();

			int rid = getRackID(book.getbCategory());

			String query = "insert into book values(?,?,?,?,?,?,?)";

			PreparedStatement pst = con.prepareStatement(query);

			pst.setInt(1, book.getBid());
			pst.setString(2, book.getbName());
			pst.setString(3, book.getbAuthor());
			pst.setString(4, book.getbCategory());
			pst.setInt(5, rid);
			pst.setInt(6, 0);
			pst.setDate(7, null);

			i = pst.executeUpdate();

			con.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			i = 0;
		}
		return i;
	}

	public int getRackID(String category) throws Exception {

		Connection con = getConnection();

		String query = "select rid from rack where rname = '" + category + "'";

		Statement st = con.createStatement();

		ResultSet rs = st.executeQuery(query);

		int rid = 0;

		if (rs.next()) {
			rid = rs.getInt(1);
		}

		con.close();

		return rid;

	}

	public int issueBookRepository(IssueBook issueBook) throws Exception {
		Connection con = getConnection();

		String query = "update book set sid = ?,idate=? where bid = ?";

		PreparedStatement pst = con.prepareStatement(query);
		Date currentDate = Date.valueOf(LocalDate.now());
		pst.setInt(1, issueBook.getSid());
		pst.setDate(2, currentDate);
		pst.setInt(3, issueBook.getBid());
		int rs = pst.executeUpdate();
		pst.executeUpdate();

		con.close();

		return rs;
	}

	public String getQualification(int sid) throws Exception {
		Connection con = getConnection();

		String query = "select qualification from student where sid = ?";

		PreparedStatement pst = con.prepareStatement(query);

		pst.setInt(1, sid);

		ResultSet rs = pst.executeQuery();

		String qualification = "";
		if (rs.next()) {
			qualification = rs.getString(1);
		}

		con.close();
		return qualification;
	}

	public boolean bookAvailabilityRepository(int bid) throws Exception {
		Connection con = getConnection();

		String query = "select * from book where bid = ? and sid = 0";

		PreparedStatement pst = con.prepareStatement(query);

		pst.setInt(1, bid);

		ResultSet rs = pst.executeQuery();

		if (rs.next()) {
			return true;
		}

		con.close();
		return false;
	}

	public int getNoOfBooksToIssue(String qualification) throws Exception {
		Connection con = getConnection();

		String query = "select no_ofBook from book_criteria where qualification = ?";

		PreparedStatement pst = con.prepareStatement(query);

		pst.setString(1, qualification);

		ResultSet rs = pst.executeQuery();

		int noOfBooks = 0;
		if (rs.next()) {
			noOfBooks = rs.getInt(1);
		}

		con.close();

		return noOfBooks;

	}

	public int getNoOfBooksIssued(int sid) throws Exception {

		Connection con = getConnection();

		String query = "select count(*) from book where sid = ?";

		PreparedStatement pst = con.prepareStatement(query);

		pst.setInt(1, sid);

		ResultSet rs = pst.executeQuery();

		int noOfBooks = 0;
		if (rs.next()) {
			noOfBooks = rs.getInt(1);
		}

		con.close();

		return noOfBooks;

	}

	public int returnBookRepository(IssueBook issueBook) throws Exception {
		Connection con = getConnection();

		String query = "update book set sid = 0,idate=null where bid = ?";

		PreparedStatement pst = con.prepareStatement(query);

		pst.setInt(1, issueBook.getBid());

		int rs = pst.executeUpdate();

		con.close();
		return rs;
	}

	public int noOfDaysSinceIssued(int bid) throws Exception {

		Connection con = getConnection();

		String query = "select DATEDIFF(CURRENT_DATE,idate) AS days from book where bid = ?";

		PreparedStatement pst = con.prepareStatement(query);

		pst.setInt(1, bid);

		ResultSet rs = pst.executeQuery();

		int daysIssued = 0;
		if (rs.next()) {
			daysIssued = rs.getInt(1);
		}

		con.close();

		return daysIssued;

	}

	public int noOfDaysCanBeIssued(int sid) throws Exception {
		Connection con = getConnection();

		String query = "select no_ofDays from book_criteria where qualification = (select qualification from student where sid = ?)";

		PreparedStatement pst = con.prepareStatement(query);

		pst.setInt(1, sid);

		ResultSet rs = pst.executeQuery();

		int daysIssued = 0;
		if (rs.next()) {
			daysIssued = rs.getInt(1);
		}

		con.close();

		return daysIssued;
	}

	public static Connection getConnection() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/practice", "root", "Ajay@143");

		return con;
	}

	public boolean isBookIssued(IssueBook issueBook) throws Exception {

		Connection con = getConnection();

		String query = "select count(*) from book where sid = ? and bid=?";

		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1, issueBook.getSid());
		pst.setInt(2, issueBook.getBid());

		ResultSet rs = pst.executeQuery();
   Boolean msg=true;
		
	if (rs.next()) {
		msg = true;
	} else
		msg = false;

		con.close();
return msg;
		
	}


	public boolean searchBookRepo(SearchBook searchBook) throws Exception {

		Connection con = getConnection();

		String query = "select count(*) from book where bid=?";

		PreparedStatement pst = con.prepareStatement(query);
		
		pst.setInt(1, searchBook.getBid());

		ResultSet rs = pst.executeQuery();
   Boolean msg=true;
		
	if (rs.next()) {
		msg = true;
	} else
		msg = false;

		con.close();
return msg;
	}
	}
