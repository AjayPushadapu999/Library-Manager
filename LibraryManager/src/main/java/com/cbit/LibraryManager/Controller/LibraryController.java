package com.cbit.LibraryManager.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cbit.LibraryManager.model.Book;
import com.cbit.LibraryManager.model.IssueBook;
import com.cbit.LibraryManager.model.SearchBook;
import com.cbit.LibraryManager.service.LibraryService;
@Controller
@ResponseBody
public class LibraryController {
	@Autowired
	LibraryService service;
@PostMapping("/addBook")
public String addBookController(@RequestBody Book book) throws Exception {
return service.addBookService(book);}
@PostMapping("/addBookt")
public String addBookController(@RequestParam Map<String, String> body, ModelAndView model) throws Exception {
    System.out.println(body);
    
    Book book = new Book(
            Integer.parseInt(body.get("bid")),
            body.get("bName"),
            body.get("bCategory"),
            body.get("bAuthor"),
            null
    );

    String addMessage = service.addBookService(book);  
    
    return addMessage;
}



@PostMapping("/issueBook")
public String  issueBookController(@RequestParam Map<String, String> body, ModelAndView model) throws Exception {
	IssueBook issueBook=new IssueBook( Integer.parseInt(body.get("sid")), Integer.parseInt(body.get("bid")) );
	String data=service.issueBookService(issueBook);
	 return data;
	
	
}
@PostMapping("/issueBookt")
public String  issueBookController(@RequestBody IssueBook issuebook) throws Exception {
	 String data=service.issueBookService(issuebook);
	 return data;
}
/*
@PostMapping("/returnBook")
public String returnBookController(@RequestBody IssueBook issueBook) throws Exception {
	return service.returnBookService(issueBook);
}*/
@PostMapping("/returnBook")
public String  returnBookController(@RequestParam Map<String, String> body, ModelAndView model) throws Exception {
	IssueBook issueBook=new IssueBook( Integer.parseInt(body.get("sid")), Integer.parseInt(body.get("bid")) );
	String data=service.returnBookService(issueBook);
	 return data;
	
	
}
@PostMapping("/bookAvailability")
public String  searchBookController(@RequestParam Map<String, String> body, ModelAndView model) throws Exception {
	SearchBook searchBook=new SearchBook( Integer.parseInt(body.get("bid")) );
String message=service.searchBookService(searchBook);
	 return message;
	
}}