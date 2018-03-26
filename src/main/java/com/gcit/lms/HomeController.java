package com.gcit.lms;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;
import com.gcit.lms.service.AdminBookService;
import com.gcit.lms.service.AdminBranchService;
import com.gcit.lms.service.AdminLoanService;
import com.gcit.lms.service.BorrowerService;
import com.gcit.lms.service.LibrarianService;

/**
 * Handles requests for the application home page.
 */
@RestController
public class HomeController {

	@Autowired
	AdminBookService adminBookService;
	@Autowired 
	AdminBranchService adminBranchService;
	@Autowired
	AdminLoanService adminLoanService;
	@Autowired
	LibrarianService librarianService;
	@Autowired
	BorrowerService borrowerService;
	@Autowired 
	AuthorDAO authorDao;
	@Autowired 
	BookCopiesDAO bcopies;
	
	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);
	
	

@RequestMapping(value = "/viewbranches", method = RequestMethod.GET,produces= { "application/XML","application/json"})
	public List<Branch> viewAuthors() throws SQLException {
	

List<Branch> lbrach=adminBranchService.getAllBranches(1, null);
	for(Branch b : lbrach) {
		
		b.setBookCopies(bcopies.Allcopies(b.getBranchId()));
		
	}	
return lbrach;
}
//	
//	@RequestMapping(value="viewauthors2",method=RequestMethod.GET,produces="json")
//	public List<Author>listAuthors(){
//		List<Author> authors=adminBookServices();
//		if(authors.isEmpty()) {
//			return new logger(HttpStatus.NO_CONTENT);
//			
//		}
//		return new List<Author>(authors,HttpStatus.OK);
//	}


	@RequestMapping(value = "/", method = RequestMethod.GET,produces= {"application/XML","application/json"})
//	@ResponseBody
	public String home() {
		return this.home();
	}
	
	@RequestMapping(value = "/andmin", method = RequestMethod.GET,produces={"application/XML","application/json"})
	@ResponseBody
	public String admin() {
		return "admin";
	}
	
	@RequestMapping(value = "/adminbookservices", method = RequestMethod.GET,produces={"application/XML","application/json"})
	@ResponseBody
	public String adminBookServices() {
		return this.adminBookServices();
	}

	@RequestMapping(value = "/viewauthors/{author}", method = RequestMethod.GET,produces={"application/XML","application/json"})
	@ResponseBody
//	public ReponseEntity<List<Author>> viewAuthors() ){
//		List<Author>authors=adminBookService.getAuthorsCount();
//		if(authors.isEmpty()) {
//			return new ResponseEntity(HttpStatu.NO_CONTENT);
//			logger.error("authors not found");
//			
//		}
//		return new ResponseEntity<List<Author>>(authors,HttpStatus.OK);
//	}
//	
	
	
	
	public String viewAuthors(Model model) throws SQLException {
		model.addAttribute("authors", adminBookService.getAllAuthors(1, null));
		Integer authCount = adminBookService.getAuthorsCount();
		int pages = 0;
		pages = (int) Math.ceil(1.0 * authCount / 10);
		model.addAttribute("pages", pages);
		return "viewauthors";
	}

	@RequestMapping(value = "/viewbooks", method = RequestMethod.GET,produces={"application/XML","application/json"})
	@ResponseBody
	public String viewBooks(Model model) throws SQLException {
		model.addAttribute("books", adminBookService.getAllBooks(1, null));
		Integer bookCount = adminBookService.getBooksCount();
		int pages = 0;
		pages = (int) Math.ceil(1.0 * bookCount / 10);
		model.addAttribute("pages", pages);
		return "viewbooks";
	}

	@RequestMapping(value = "/viewpublishers", method = RequestMethod.GET,produces={"application/XML","application/json"})
	@ResponseBody
	public String viewPublishers(Model model) throws SQLException {
		model.addAttribute("publishers",
				adminBookService.getAllPublishers(1, null));
		Integer pubCount = adminBookService.getPublishersCount();
		int pages = 0;
		pages = (int) Math.ceil(1.0 * pubCount / 10);
		model.addAttribute("pages", pages);
		return "viewpublishers";
	}

	@RequestMapping(value = "/viewgenres", method = RequestMethod.GET,produces={"application/XML","application/json"})
	@ResponseBody
	public String viewGenres(Model model) throws SQLException {
		model.addAttribute("genres", adminBookService.getAllGenres(1, null));
		Integer genreCount = adminBookService.getGenresCount();
		int pages = 0;
		pages = (int) Math.ceil(1.0 * genreCount / 10);
		model.addAttribute("pages", pages);
		return "viewgenres";
	}
	
	@RequestMapping(value = "/viewbranches1", method = RequestMethod.GET,produces={"application/XML","application/json"})
	@ResponseBody
	public String viewBranches(Model model) throws SQLException {
		model.addAttribute("branches", adminBranchService.getAllBranches(1, null));
		Integer branchCount = adminBranchService.getBranchesCount();
		int pages = 0;
		pages = (int) Math.ceil(1.0 * branchCount / 10);
		model.addAttribute("pages", pages);
		return "viewbranches";
	}
	
	@RequestMapping(value = "/viewloans", method = RequestMethod.GET,produces="application/json")
	@ResponseBody
	public String viewLoans(Model model) throws SQLException {
		model.addAttribute("loans", adminLoanService.getAllLoans(1, null));
		Integer loanCount = adminLoanService.getLoansCount();
		int pages = 0;
		pages = (int) Math.ceil(1.0 * loanCount / 10);
		model.addAttribute("pages", pages);
		return "viewloans";
	}

	@RequestMapping(value = "/createbook", method = RequestMethod.GET,produces={"application/XML","application/json"})
	@ResponseBody
	public String createBook(Model model) throws SQLException {
		model.addAttribute("authors",
				adminBookService.getAllAuthors(null, null));
		model.addAttribute("publishers",
				adminBookService.getAllPublishers(null, null));
		model.addAttribute("genres", adminBookService.getAllGenres(null, null));
		return "createbook";
	}
	
	@RequestMapping(value = "/createbook", method = RequestMethod.POST,produces={"application/XML","application/json"})
	@ResponseBody
	public String createBookPost(
			Model model,
			@RequestParam("bookName") String title,
			@RequestParam("publisherId") Integer pubId,
			@RequestParam(value = "authorId[]", required = false) Integer[] authorIds,
			@RequestParam(value = "genreId[]", required = false) Integer[] genreIds)
			throws SQLException {

		List<Author> bookAuthors = new ArrayList<Author>();
		List<Genre> bookGenres = new ArrayList<Genre>();
		List<Book> books=new ArrayList<Book>();
		if (authorIds != null) {
			for (Integer authorId : authorIds) {
				bookAuthors.add(adminBookService.getAuthorByPK(authorId));
			}
		}

		if (genreIds != null) {
			for (Integer genreId : genreIds) {
				bookGenres.add(adminBookService.getGenreByPK(genreId));
			}
		}
		Book book = new Book();
		book.setTitle(title);
		if (pubId != null) {
			book.setPublisher(adminBookService.getPublisherByPK(pubId));
		}
		book.setBookId(adminBookService.saveBookWithId(book));

		for (Author author : bookAuthors) {
			adminBookService.setBookAuthor(book, author);
		}
		for (Genre genre : bookGenres) {
			adminBookService.setBookGenre(book, genre);
		}

		Integer bookCount = adminBookService.getBooksCount();
		int pages = 0;
		pages = (int) Math.ceil(1.0 * bookCount / 10);
		model.addAttribute("pages", pages);
		model.addAttribute("books", adminBookService.getAllBooks(1, null));
		return "viewbooks";
	}
	
	
	
	
	@RequestMapping(value = "/editbook", method = RequestMethod.GET,produces={"application/XML","application/json"})
	@ResponseBody
	public String editBook(Model model, @RequestParam("bookId") Integer bookId)
			throws SQLException {
		Book book = adminBookService.getBookByPK(bookId);
		model.addAttribute("book", book);
		model.addAttribute("authors",adminBookService.getAllAuthors(null, null));
		model.addAttribute("publishers", adminBookService.getAllPublishers(null, null));
		model.addAttribute("genres", adminBookService.getAllGenres(null, null));
		return "editbook";
	}

	@RequestMapping(value = "/editbook", method = RequestMethod.POST,produces={"application/XML","application/json"})
	@ResponseBody
	public String editBookPost(
			Model model,
			@RequestParam("bookId") Integer bookId,
			@RequestParam("bookName") String title,
			@RequestParam("publisherId") Integer pubId,
			@RequestParam(value = "authorId[]", required = false) Integer[] authorIds,
			@RequestParam(value = "genreId[]", required = false) Integer[] genreIds)
			throws SQLException {
		
		ArrayList<Author> bookAuthors = new ArrayList<Author>();
		ArrayList<Genre> bookGenres = new ArrayList<Genre>();
		if (authorIds != null) {
			for (Integer authorId : authorIds) {
				bookAuthors.add(adminBookService.getAuthorByPK(authorId));
			}
		}
		if (genreIds != null) {
			for (Integer genreId : genreIds) {
				bookGenres.add(adminBookService.getGenreByPK(genreId));
			}
		}
		Book book = new Book();
		book.setBookId(bookId);
		book.setTitle(title);
		if(pubId == 0){
			book.setPublisher(null);
		}
		else if(pubId != null){
			book.setPublisher(adminBookService.getPublisherByPK(pubId));
		}
		adminBookService.saveBook(book);

		book = adminBookService.getBookByPK(bookId);
		if (!bookAuthors.isEmpty()){
			adminBookService.editBookAuthors(book, bookAuthors);
		}
		if (!bookGenres.isEmpty()){
			adminBookService.editBookGenres(book, bookGenres);
		}
		
		Integer bookCount = adminBookService.getBooksCount();
		int pages = 0;
		pages = (int) Math.ceil(1.0 * bookCount / 10);
		model.addAttribute("pages", pages);
		model.addAttribute("books", adminBookService.getAllBooks(1, null));
		return "viewbooks";
	}
	
	
	@RequestMapping(value = "/deletebook")
	@ResponseBody
	public String deleteBook(Model model, 
			@RequestParam("bookId") Integer bookId,
			@RequestParam("pageNo") Integer pageNo,
			@RequestParam("searchString") String searchString) throws SQLException{
		Book book = new Book();
		book.setBookId(bookId);
		adminBookService.deleteBook(book);
		List<Book> books = adminBookService.getAllBooks(pageNo, searchString);
		model.addAttribute("books", books);
		return bookTable(books);
	}
	
	@RequestMapping(value = "/createauthor", method = RequestMethod.GET,produces={"application/XML","application/json"})
	@ResponseBody
	public String createAuthor() throws SQLException {
		return "createauthor";
	}
	
	@RequestMapping(value = "/createauthor", method = RequestMethod.POST,produces={"application/XML","application/json"})
	@ResponseBody
	public String createAuthorPost(Model model,
			@RequestParam("authorName") String authorName) throws SQLException {
		Author author = new Author();
		author.setAuthorName(authorName);
		adminBookService.saveAuthor(author);
		
		model.addAttribute("authors", adminBookService.getAllAuthors(1, null));
		Integer authCount = adminBookService.getAuthorsCount();
		int pages = 0;
		pages = (int) Math.ceil(1.0 * authCount / 10);
		model.addAttribute("pages", pages);
		return "viewauthors";
	}
	
	@RequestMapping(value = "/editauthor", method = RequestMethod.GET,produces={"application/XML","application/json"})
	@ResponseBody
	public String editAuthor(Model model, @RequestParam("authorId") Integer authorId) throws SQLException {
		Author author = adminBookService.getAuthorByPK(authorId);
		model.addAttribute("author", author);
		return "editauthor";
	}
	
	@RequestMapping(value = "/editauthor", method = RequestMethod.POST,produces="application/json")
	@ResponseBody
	public String editAuthorPost(Model model, 
			@RequestParam("authorId") Integer authorId,
			@RequestParam("authorName") String authorName) throws SQLException {
		Author author = new Author();
		author.setAuthorId(authorId);
		author.setAuthorName(authorName);
		adminBookService.saveAuthor(author);
		
		model.addAttribute("authors", adminBookService.getAllAuthors(1, null));
		Integer authCount = adminBookService.getAuthorsCount();
		int pages = 0;
		pages = (int) Math.ceil(1.0 * authCount / 10);
		model.addAttribute("pages", pages);
		return "viewauthors";
	}
	
	
	@RequestMapping(value = "/deleteauthor")
	public String deleteAuthor(Model model, 
			@RequestParam("authorId") Integer authorId,
			@RequestParam("pageNo") Integer pageNo,
			@RequestParam("searchString") String searchString) throws SQLException{
		Author author = new Author();
		author.setAuthorId(authorId);
		adminBookService.deleteAuthor(author);
		List<Author> authors = adminBookService.getAllAuthors(pageNo, searchString);
		model.addAttribute("authors", authors);
		return authorTable(authors);
	}

	@RequestMapping(value = "/createbranch", method = RequestMethod.GET,produces={"application/XML","application/json"})
	@ResponseBody
	public String createBranch() throws SQLException {
		return "createbranch";
	}
	
	@RequestMapping(value = "/createbranch", method = RequestMethod.POST,produces={"application/XML","application/json"})
	@ResponseBody
	public String createBranchPost(Model model,
			@RequestParam("branchName") String branchName,
			@RequestParam("branchAddress") String branchAddress) throws SQLException {
		Branch branch = new Branch();
		branch.setBranchName(branchName);
		branch.setBranchAddress(branchAddress);
		adminBranchService.saveBranch(branch);
		
		model.addAttribute("branches", adminBranchService.getAllBranches(1, null));
		Integer pubCount = adminBranchService.getBranchesCount();
		int pages = 0;
		pages = (int) Math.ceil(1.0 * pubCount / 10);
		model.addAttribute("pages", pages);
		return "viewbranches";
	}
	
	@RequestMapping(value = "/admineditbranch", method = RequestMethod.GET,produces="application/json")
	@ResponseBody
	public String editBranch(Model model, 
			@RequestParam("branchId") Integer branchId) throws SQLException {
		Branch branch = adminBranchService.getBranchById(branchId);
		model.addAttribute("branch", branch);
		return "admineditbranch";
	}
	
	@RequestMapping(value = "/admineditbranch", method = RequestMethod.POST,produces="application/json")
	@ResponseBody
	public String editBranchPost(Model model, 
			@RequestParam("branchId") Integer branchId,
			@RequestParam("branchName") String branchName,
			@RequestParam("branchAddress") String branchAddress) throws SQLException {
		Branch branch = new Branch();
		branch.setBranchId(branchId);
		branch.setBranchName(branchName);
		branch.setBranchAddress(branchAddress);
		adminBranchService.saveBranch(branch);
		
		model.addAttribute("branches", adminBranchService.getAllBranches(1, null));
		Integer branchCount = adminBranchService.getBranchesCount();
		int pages = 0;
		pages = (int) Math.ceil(1.0 * branchCount / 10);
		model.addAttribute("pages", pages);
		return "viewbranches";
	}
	
	@ResponseBody
	@RequestMapping(value = "/deletebranch")
	public String deleteBranch(Model model, 
			@RequestParam("branchId") Integer branchId,
			@RequestParam("pageNo") Integer pageNo,
			@RequestParam("searchString") String searchString) throws SQLException{
		Branch branch = new Branch();
		branch.setBranchId(branchId);
		adminBranchService.deleteBranch(branch);
		List<Branch> branches = adminBranchService.getAllBranches(pageNo, searchString);
		return branchTable(branches);
	}
	
	@RequestMapping(value = "/createpublisher", method = RequestMethod.GET,produces="application/json")
	@ResponseBody
	public String createPublisher() throws SQLException {
		return "createpublisher";
	}
	
	@RequestMapping(value = "/createpublisher", method = RequestMethod.POST,produces="application/json")
	@ResponseBody
	public String createPublisherPost(Model model,
			@RequestParam("publisherName") String publisherName,
			@RequestParam("publisherAddress") String publisherAddress,
			@RequestParam("publisherPhone") String publisherPhone) throws SQLException {
		Publisher publisher = new Publisher();
		publisher.setPublisherName(publisherName);
		publisher.setPublisherAddress(publisherAddress);
		publisher.setPublisherPhone(publisherPhone);
		adminBookService.savePublisher(publisher);
		
		model.addAttribute("publishers", adminBookService.getAllPublishers(1, null));
		Integer pubCount = adminBookService.getPublishersCount();
		int pages = 0;
		pages = (int) Math.ceil(1.0 * pubCount / 10);
		model.addAttribute("pages", pages);
		return "viewpublishers";
	}
	
	@RequestMapping(value = "/editpublisher", method = RequestMethod.GET)
	@ResponseBody
	public String editPublisher(Model model, @RequestParam("publisherId") Integer publisherId) throws SQLException {
		Publisher publisher = adminBookService.getPublisherByPK(publisherId);
		model.addAttribute("publisher", publisher);
		return "editpublisher";
	}
	
	@RequestMapping(value = "/editpublisher", method = RequestMethod.POST,produces="application/json")
	@ResponseBody
	public String editPublisherPost(Model model,
			@RequestParam("publisherId") Integer publisherId,
			@RequestParam("publisherName") String publisherName,
			@RequestParam("publisherAddress") String publisherAddress,
			@RequestParam("publisherPhone") String publisherPhone) throws SQLException {
		Publisher publisher = new Publisher();
		publisher.setPublisherId(publisherId);
		publisher.setPublisherName(publisherName);
		publisher.setPublisherAddress(publisherAddress);
		publisher.setPublisherPhone(publisherPhone);
		adminBookService.savePublisher(publisher);
		
		model.addAttribute("publishers", adminBookService.getAllPublishers(1, null));
		Integer pubCount = adminBookService.getPublishersCount();
		int pages = 0;
		pages = (int) Math.ceil(1.0 * pubCount / 10);
		model.addAttribute("pages", pages);
		return "viewpublishers";
	}
	

	@RequestMapping(value = "/deletepublisher")
	@ResponseBody
	public String deletePublisher(Model model, 
			@RequestParam("publisherId") Integer publisherId,
			@RequestParam("pageNo") Integer pageNo,
			@RequestParam("searchString") String searchString) throws SQLException{
		Publisher publisher = new Publisher();
		publisher.setPublisherId(publisherId);
		adminBookService.deletePublisher(publisher);
		List<Publisher> publishers = adminBookService.getAllPublishers(pageNo, searchString);
		model.addAttribute("publishers", publishers);
		return publisherTable(publishers);
	}
	
	@RequestMapping(value = "/creategenre", method = RequestMethod.GET)
	public String createGenre() throws SQLException {
		return "creategenre";
	}

	@RequestMapping(value = "/creategenre", method = RequestMethod.POST)
	public String createGenrePost(Model model,
			@RequestParam("genreName") String genreName) throws SQLException {
		Genre genre = new Genre();
		genre.setGenreName(genreName);
		adminBookService.saveGenre(genre);
		
		model.addAttribute("genres", adminBookService.getAllGenres(1, null));
		Integer genreCount = adminBookService.getGenresCount();
		int pages = 0;
		pages = (int) Math.ceil(1.0 * genreCount / 10);
		model.addAttribute("pages", pages);
		return "viewgenres";
	}
	
	@RequestMapping(value = "/editgenre", method = RequestMethod.GET,produces="application/json")
	public String editGenre(Model model, @RequestParam("genreId") Integer genreId) throws SQLException {
		Genre genre = adminBookService.getGenreByPK(genreId);
		model.addAttribute("genre", genre);
		return "editgenre";
	}
	
	@RequestMapping(value = "/editgenre", method = RequestMethod.POST)
	public String editGenrePost(Model model, 
			@RequestParam("genreId") Integer genreId,
			@RequestParam("genreName") String genreName) throws SQLException {
		Genre genre = new Genre();
		genre.setGenreId(genreId);
		genre.setGenreName(genreName);
		adminBookService.saveGenre(genre);
		
		model.addAttribute("genres", adminBookService.getAllGenres(1, null));
		Integer genreCount = adminBookService.getGenresCount();
		int pages = 0;
		pages = (int) Math.ceil(1.0 * genreCount / 10);
		model.addAttribute("pages", pages);
		return "viewgenres";
	}
	
	@ResponseBody
	@RequestMapping(value = "/deletegenre")
	public String deleteGenre(Model model, 
			@RequestParam("genreId") Integer genreId,
			@RequestParam("pageNo") Integer pageNo,
			@RequestParam("searchString") String searchString) throws SQLException{
		Genre genre = new Genre();
		genre.setGenreId(genreId);
		adminBookService.deleteGenre(genre);
		List<Genre> genres = adminBookService.getAllGenres(pageNo, searchString);
		model.addAttribute("genres", genres);
		return genreTable(genres);
	}

	@ResponseBody
	@RequestMapping(value = "/bookPagination")
	public String bookPagination(Model model, @RequestParam("pageNo") Integer pageNo,
			@RequestParam("searchString") String searchString) throws SQLException{
		List<Book> books = adminBookService.getAllBooks(pageNo, searchString);
		model.addAttribute("books", books);
		return bookTable(books);
	}
	
	@ResponseBody
	@RequestMapping(value = "/authorPagination")
	public String authorPagination(Model model, @RequestParam("pageNo") Integer pageNo,
			@RequestParam("searchString") String searchString) throws SQLException{
		List<Author> authors = adminBookService.getAllAuthors(pageNo, searchString);
		return authorTable(authors);
	}
	
	@ResponseBody
	@RequestMapping(value = "/publisherPagination")
	public String publisherPagination(Model model, @RequestParam("pageNo") Integer pageNo,
			@RequestParam("searchString") String searchString) throws SQLException{
		List<Publisher> publishers = adminBookService.getAllPublishers(pageNo, searchString);
		return publisherTable(publishers);
	}
	
	@ResponseBody
	@RequestMapping(value = "/genrePagination")
	public String genrePagination(Model model, @RequestParam("pageNo") Integer pageNo,
			@RequestParam("searchString") String searchString) throws SQLException{
		List<Genre> genres = adminBookService.getAllGenres(pageNo, searchString);
		return genreTable(genres);
	}
	
	@ResponseBody
	@RequestMapping(value = "/branchpagination")
	public String branchPagination(Model model, @RequestParam("pageNo") Integer pageNo,
			@RequestParam("searchString") String searchString) throws SQLException{
		List<Branch> branches = adminBranchService.getAllBranches(pageNo, searchString);
		return branchTable(branches);
	}

	@ResponseBody
	@RequestMapping(value = "/loanpagination")
	public String loanPagination(Model model, @RequestParam("pageNo") Integer pageNo,
			@RequestParam("searchString") String searchString) throws SQLException{
		List<BookLoans> loans = adminLoanService.getAllLoans(pageNo, searchString);
		return loansTable(loans);
	}
	
	@ResponseBody
	@RequestMapping(value = "/extendloan" ,method = RequestMethod.GET)
	public String extendLoan(Model model,
			@RequestParam("pageNo") Integer pageNo,
			@RequestParam("borrowerId") Integer cardNo,
			@RequestParam("bookId") Integer bookId,
			@RequestParam("branchId") Integer branchId,
			@RequestParam("dateOut") String dateOut) throws SQLException{
		BookLoans loan = new BookLoans();
		loan = adminLoanService.getLoanByPK(cardNo, bookId, branchId, dateOut);
		adminLoanService.overwriteLoan(loan);

		List<BookLoans> loans = adminLoanService.getAllLoans(pageNo, null);
		return loansTable(loans);
	}

	@RequestMapping(value = "/librarianbranch", method = RequestMethod.GET)
	private String librarianBranch(Model model) throws SQLException{
		List<Branch> branches = new ArrayList<>();
		branches = librarianService.getAllBranches(null, null);
		model.addAttribute("branches", branches);
		model.addAttribute("selectedBranch", (int)0);
		return "librarianbranch";
	}

	@ResponseBody
	@RequestMapping(value = "/branchcopies", method = RequestMethod.GET,produces="application/json")
	public String branchCopies(Model model,
			@RequestParam("branchId") Integer branchId) throws SQLException{
		String bookCopiesTable = "";
		if(branchId != 0){
			Branch branch = new Branch(); 
			branch.setBranchId(branchId);
			List<BookCopies> bookcopies = librarianService.getBookCopiesInBranch(branch);
			bookCopiesTable = bookCopiesTable(bookcopies);
		}
		return bookCopiesTable;
	}
	
	@RequestMapping(value = "/editbranch", method = RequestMethod.GET)
	public String editLibBranch(Model model, 
			@RequestParam("branchId") Integer branchId) throws SQLException {
		Branch branch = adminBranchService.getBranchById(branchId);
		List<Book> books = adminBookService.getAllBooks(null, null);
		books.removeAll(librarianService.getAllBooksInBranch(branch));
		model.addAttribute("branch", branch);
		model.addAttribute("books", books);
		return "editbranch";
	}
	
	@RequestMapping(value = "/editbranch", method = RequestMethod.POST)
	public String editLibBranchPost(Model model, 
			@RequestParam("branchId") Integer branchId,
			@RequestParam("branchName") String branchName,
			@RequestParam("branchAddress") String branchAddress,
			@RequestParam(value="addBooks[]", required=false) Integer[] bookIds) throws SQLException {
			
		List<Book> addBooks = new ArrayList<Book>();
		Book addBook=null;
		if (bookIds != null){
			for (Integer bookId:bookIds){
				addBook = new Book();
				addBook.setBookId(bookId);
				addBooks.add(addBook);
			}
		}
		
		Branch editBranch = new Branch();
		editBranch.setBranchId(branchId);
		editBranch.setBranchName(branchName);
		editBranch.setBranchAddress(branchAddress);
		librarianService.updateBranch(editBranch);
		if(addBooks!=null && !addBooks.isEmpty()){
			librarianService.addBookCopies(addBooks, editBranch);
		}
		model.addAttribute("branches", librarianService.getAllBranches(null, null));
		model.addAttribute("selectedBranch", editBranch.getBranchId());
		return "librarianbranch";
	}
	
	@RequestMapping(value = "/addbookcopies", method = RequestMethod.GET)
	public String addBookCopies(Model model, 
			@RequestParam("bookId") Integer bookId,
			@RequestParam("branchId") Integer branchId) throws SQLException {
		model.addAttribute("book", librarianService.getBookByPK(bookId));
		model.addAttribute("branch", librarianService.getBranchByPK(branchId));
		return "addbookcopies";
	}
	
	@RequestMapping(value = "/addbookcopies", method = RequestMethod.POST)
	public String addBookCopiesPost(Model model, 
			@RequestParam("branchId") Integer branchId,
			@RequestParam("bookId") Integer bookId,
			@RequestParam("copies") Integer copies) throws SQLException {
			
		BookCopies bc = librarianService.getBookCopiesByPK(branchId, bookId);
		bc.setCopies(bc.getCopies()+copies);
		librarianService.updateBookCopies(bc);
		
		model.addAttribute("branches", librarianService.getAllBranches(null, null));
		model.addAttribute("selectedBranch", branchId);
		return "librarianbranch";
	}
	
	@RequestMapping(value = "/borrower", method = RequestMethod.GET)
	public String borrower(Model model) throws SQLException {
		model.addAttribute("branches", librarianService.getAllBranches(null, null));
		return "borrower";
	}
	
	@ResponseBody
	@RequestMapping(value = "/borrowerlogin", method = RequestMethod.POST)
	public String borrowerLogin(Model model,
			@RequestParam("cardNo") Integer cardNo) throws SQLException{
		Borrower borrower = null;
		String borrowerDetails = "";
		try {
			borrower = borrowerService.getBorrower(cardNo);
			borrowerDetails = borrower.getCardNo() + "//" + borrower.getName();
		} catch (Exception e) {
			borrowerDetails = "failed";
		}	
		return borrowerDetails;
	}
	
	@ResponseBody
	@RequestMapping(value = "/fetchbooks", method = RequestMethod.POST)
	public String fetchBooks(Model model,
			@RequestParam("branchId") Integer branchId) throws SQLException{
		List <BookCopies> bookcopies = new ArrayList<BookCopies>();
		if(branchId != 0){
			Branch branch = new Branch();
			branch.setBranchId(branchId);
			bookcopies = borrowerService.getBookCopiesInBranch(branch);
		}
		return borrowerBookCopiesTable(bookcopies);
	}
	
	@ResponseBody
	@RequestMapping(value = "/fetchloans", method = RequestMethod.POST)
	public String fetchLoans(Model model,
			@RequestParam("cardNo") Integer cardNo) throws SQLException{
		Borrower br = new Borrower(); 
		br.setCardNo(cardNo);
		List<BookLoans> bookloans = new ArrayList<BookLoans>();
		bookloans = borrowerService.getAllBorrowerLoans(br);
		return borrowerTable(bookloans);
	}
	
	@ResponseBody
	@RequestMapping(value = "/checkoutbook", method = RequestMethod.POST)
	public String checkOutBook(Model model,
			
			@RequestParam("cardNo") Integer cardNo,
			@RequestParam("bookId") Integer bookId,
			@RequestParam("branchId") Integer branchId) throws SQLException{
		Book book= new Book();
		book.setBookId(bookId);
		Branch branch = new Branch();
		branch.setBranchId(branchId);
		Borrower borrower = new Borrower(); 
		borrower.setCardNo(cardNo);
		BookCopies bc = new BookCopies();
		bc.setBook(book);
		bc.setBranch(branch);
		List<BookLoans> updatedbookloans = new ArrayList<BookLoans>();
		List<BookCopies> updatedbookcopies = new ArrayList<BookCopies>();
		borrowerService.checkOutBook(bc, borrower);
		updatedbookloans = borrowerService.getAllBorrowerLoans(borrower);
		updatedbookcopies = borrowerService.getBookCopiesInBranch(branch);
		return borrowerTable(updatedbookloans) + "//" + borrowerBookCopiesTable(updatedbookcopies);
	}
	
	@ResponseBody
	@RequestMapping(value = "/returnbook", method = RequestMethod.POST)
	public String returnBook(Model model,
			@RequestParam("cardNo") Integer cardNo,
			@RequestParam("bookId") Integer bookId,
			@RequestParam("branchId") Integer branchId,
			@RequestParam("dateOut") String dateOut) throws SQLException{
		Book book= new Book();
		book.setBookId(bookId);
		Branch branch = new Branch();
		branch.setBranchId(branchId);
		Borrower borrower = new Borrower(); 
		borrower.setCardNo(cardNo);
		BookLoans bl = new BookLoans();
		bl.setBook(book);
		bl.setBranch(branch);
		bl.setBorrower(borrower);	
		bl.setDateOut(dateOut);
		List<BookLoans> updatedbookloans = new ArrayList<BookLoans>();
		List<BookCopies> updatedbookcopies = new ArrayList<BookCopies>();
		borrowerService.returnBook(bl);
		
		updatedbookloans = borrowerService.getAllBorrowerLoans(borrower);
		updatedbookcopies = borrowerService.getBookCopiesInBranch(branch);
		return borrowerTable(updatedbookloans) + "//" + borrowerBookCopiesTable(updatedbookcopies) + "//" + branchId;
	}
	
	
	private String bookTable(List<Book> books){
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("<tr><th>Book ID</th><th>Title</th><th>Author(s)</th><th>Publisher</th><th>Genre(s)</th></tr>");
		for (Book b:books){
			strBuffer.append("<tr><td>" + (int)(books.indexOf(b)+1) + "</td><td>" + b.getTitle() + "</td><td>");
			for (Author a:b.getAuthors()) {
				strBuffer.append("|" + a.getAuthorName() + "|");
			}
			strBuffer.append("</td><td>");
			if(b.getPublisher() != null){
				strBuffer.append(b.getPublisher().getPublisherName());
			}
			strBuffer.append("</td><td>");
			for (Genre g:b.getGenres()) {
				strBuffer.append("|" + g.getGenreName() + "|");
			}
			strBuffer.append("<td><button type='button' class='btn btn-primary' data-toggle='modal' data-target='#editBookModal' href='editbook?bookId="+b.getBookId()+"'>Edit</button></td>");
			strBuffer.append("<td><button class='btn btn-danger' type='button' onClick='deleteBook("+ b.getBookId() +")'>Delete</button></td></tr>");
		}
		return strBuffer.toString();
	}
	
	private String authorTable(List<Author> authors){
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("<tr><th>Author ID</th><th>Author Name</th><th>Books by Author</th><th>Edit</th><th>Delete</th></tr>");
		for(Author a: authors){
			strBuffer.append("<tr><td>"+(int)(authors.indexOf(a)+1) +"</td><td>"+a.getAuthorName()+"</td><td>");
			for(Book b: a.getBooks()){
				strBuffer.append('|' + b.getTitle() + '|');
			}
			strBuffer.append("</td><td><button type='button' class='btn btn-sm btn-primary' data-toggle='modal' data-target='#editAuthorModal' href='editauthor?authorId="+a.getAuthorId()+"'>Edit</button></td>");
			strBuffer.append("<td><button type='button' class='btn btn-sm btn-danger' onClick='deleteAuthor("+ a.getAuthorId() +")'>Delete</button></td></tr>");
		}
		return strBuffer.toString();
	}
	
	private String publisherTable(List<Publisher> publishers){
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("<tr><th>Publisher ID</th><th>Publisher Name</th><th>Publisher Address</th><th>Publisher Phone</th><th>Edit</th><th>Delete</th></tr>");
		for (Publisher p:publishers){
			strBuffer.append("<tr><td>"+(int)(publishers.indexOf(p)+1)+"</td><td>"+p.getPublisherName()+"</td><td>"+p.getPublisherAddress()+"</td><td>"+p.getPublisherPhone()+"</td>");
			strBuffer.append("<td><button class='btn btn-primary' type='button' data-toggle='modal' data-target='#editPublisherModal' href='editpublisher?pubId="+p.getPublisherId()+"'>Edit</button></td>");
			strBuffer.append("<td><button class='btn btn-danger' type='button' onclick= 'deletePublisher("+ p.getPublisherId() +")'>Delete</button></td></tr>");
		}
		return strBuffer.toString();
	}
	
	private String genreTable(List<Genre> genres){
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("<tr><th>Genre ID</th><th>Genre Name</th><th>Edit</th><th>Delete</th></tr>");
		for (Genre g:genres){
			strBuffer.append("<tr><td>"+(int)(genres.indexOf(g)+1)+"</td><td>"+g.getGenreName()+"</td>");
			strBuffer.append("<td><button class='btn btn-primary' type='button' data-toggle='modal' data-target='#editGenreModal' href='editgenre?genreId="+g.getGenreId()+"'>Edit</button></td>");
			strBuffer.append("<td><button class='btn btn-danger' type='button' onclick= 'deleteGenre("+ g.getGenreId() +")'>Delete</button></td></tr>");
		}
		return strBuffer.toString();
	}
	
	private String branchTable(List<Branch> branches){
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("<tr><th>Branch ID</th><th>Branch Name</th><th>Branch Address</th><th>Edit</th><th>Delete</th></tr>");
		for (Branch b:branches){
			strBuffer.append("<tr><td>"+(int)(branches.indexOf(b)+1)+"</td><td>"+b.getBranchName()+"</td><td>"+b.getBranchAddress()+"</td>");
			strBuffer.append("<td><button class='btn btn-primary' type='button' data-toggle='modal' data-target='#editBranchModal' href='admineditbranch?branchId="+b.getBranchId()+"'>Edit</button></td>");
			strBuffer.append("<td><button class='btn btn-danger' type='button' onclick= 'deleteBranch("+ b.getBranchId() +")'>Delete</button></td></tr>");
		}
		return strBuffer.toString();
	}

	private String loansTable(List<BookLoans> loans){
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("<tr><th>Card No</th><th>Borrower Name</th><th>Book Title</th><th>Library Branch</th><th>Date Out</th><th>Date In</th><th>Due Date</th></tr>");
		for (BookLoans l:loans){
			strBuffer.append("<tr><td>"+ l.getBorrower().getCardNo() +"</td>");
			strBuffer.append("<td>"+ l.getBorrower().getName() +"</td>");
			strBuffer.append("<td>"+ l.getBook().getTitle() +"</td>");
			strBuffer.append("<td>"+ l.getBranch().getBranchName() +"</td>");
			strBuffer.append("<td>"+ l.getDateOut() +"</td><td>");
			if(l.getDateIn() != null){
				strBuffer.append(l.getDateIn());
			}
			strBuffer.append("</td><td>"+ l.getDueDate() +"</td>");
			if (l.getDateIn() == null){
				strBuffer.append("<td><button onclick='extendLoan("+l.getBorrower().getCardNo()+","+ l.getBook().getBookId()+","+ l.getBranch().getBranchId()+ ",\"" +l.getDateOut()+"\")' class='btn btn-primary' type='button'>Add 30 Days</button></td></tr>");
			}
		}
		return strBuffer.toString();
	}
	
	private String bookCopiesTable(List<BookCopies> bookcopies){
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("<tr><th>Book Title</th><th>Copies</th></tr>");
		for (BookCopies bc:bookcopies){
			strBuffer.append("<tr><td>" + bc.getBook().getTitle() + "</td><td>" + bc.getCopies() + "</td>");
			strBuffer.append("<td><button data-toggle='modal' data-target='#addCopiesModal' href='addbookcopies?bookId=" + bc.getBook().getBookId()+ "&branchId=" + bc.getBranch().getBranchId() +"'class='btn btn-sm btn-primary' type='button'>Add Copies</button></td></tr>");
		}
		return strBuffer.toString();
	}
	
	private String borrowerBookCopiesTable(List<BookCopies> bookcopies){
		StringBuffer strbuffer = new StringBuffer();
		strbuffer.append("<tr><th>Book Title</th><th>Author(s)</th><th>Status</th></tr>");
		for (BookCopies bc:bookcopies){
			strbuffer.append("<tr><td>" + bc.getBook().getTitle() + "</td><td>");
			if(bc.getBook().getAuthors()!=null){
				for (Author a:bc.getBook().getAuthors()){
					strbuffer.append("|" + a.getAuthorName() + "|");
				}
			}
			strbuffer.append("</td><td>");
			if (bc.getCopies() > 0){
				strbuffer.append("Available </td>");
				strbuffer.append("<td><button class='btn btn-primary btn-small' onclick='checkOutBook("+bc.getBook().getBookId()+ "," + bc.getBranch().getBranchId() +")'> Check Out </button></td>");
			}
			else{
				strbuffer.append("Out of Stock </td>");
			}
			strbuffer.append("</tr>");
		}
		return strbuffer.toString();
	}
	
	private String borrowerTable(List<BookLoans> bookLoans){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
		StringBuffer strbuffer = new StringBuffer();
		strbuffer.append("<tr><th>Branch</th><th>Book Title</th><th>Author(s)</th><th>Date Out</th><th>Due Date</th></tr>");
		for (BookLoans bl:bookLoans){
			if (bl.getDateIn() == null){
				strbuffer.append("<tr><td>" + bl.getBranch().getBranchName() + "</td><td>" + bl.getBook().getTitle() + "</td><td>");
				if(bl.getBook().getAuthors() != null){
					for (Author a:bl.getBook().getAuthors()){
						strbuffer.append("|" + a.getAuthorName() + "|");
					}
				}
				strbuffer.append("</td><td>"+LocalDateTime.parse(bl.getDateOut(), formatter)+"</td><td>"+LocalDateTime.parse(bl.getDueDate(), formatter)+"</td>");
				strbuffer.append("<td><button class='btn btn-primary btn-small' onclick='returnBook(" + bl.getBook().getBookId()+ "," + bl.getBranch().getBranchId() + "," + bl.getBorrower().getCardNo() + ",\"" + bl.getDateOut() +"\")'> Return Book</button></td>");
			}
		}
		return strbuffer.toString();
	}
	
	
	
	
//	
//	package com.gcit.lms.service;
//
//	import java.sql.SQLException;
//	import java.util.List;
//
//	import org.slf4j.Logger;
//	import org.slf4j.LoggerFactory;
//	import org.springframework.beans.factory.annotation.Autowired;
//	import org.springframework.ui.Model;
//	import org.springframework.web.bind.annotation.RequestMapping;
//	import org.springframework.web.bind.annotation.RequestMethod;
//	import org.springframework.web.bind.annotation.RequestParam;
//	import org.springframework.web.bind.annotation.ResponseBody;
//	import org.springframework.web.bind.annotation.RestController;
//
//	import com.gcit.lms.dao.BookCopiesDAO;
//	import com.gcit.lms.entity.Book;
//	import com.gcit.lms.entity.Branch;
//	import com.gcit.lms.service.AdminBranchService;
//
//	@RestController
//	//@Component
//	//@RequestMapping("/branches")
//	public class BranchController {
//		@Autowired 
//		AdminBranchService adminBranchService;
////		@Autowired
////		AdminBookService 	adminBookService;
//		@Autowired 
//		BookCopiesDAO bcopies; 
//
//
//	//
////		private static final Logger logger = LoggerFactory
////				.getLogger(BranchController.class);
//	//
////		@RequestMapping(value = "/", method = RequestMethod.GET,produces="application/json")
////		@ResponseBody
////		public String home() {
////			return "index";
////		}
//	//	
//	//	
//		@RequestMapping(value = "/viewbranches", method = RequestMethod.GET,produces="application/json")
//		public List<Branch> viewAuthors() throws SQLException {
////			System.out.print("hello");
////			model.addAttribute("branches", adminBranchService.getAllBranches(1, null));
////			Integer branchCount = adminBranchService.getBranchesCount();
////			int pages = 0;
////			pages = (int) Math.ceil(1.0 * branchCount / 10);
////			model.addAttribute("pages", pages);
//			
//			
//		
//			List<Branch> lbrach=adminBranchService.getAllBranches(1, null);
//			for(Branch b : lbrach) {
//				
//				b.setBookCopies(bcopies.Allcopies(b.getBranchId()));
//				
//			}
//			
//			return lbrach;
//		}
//	//	
////		@RequestMapping(value = "/deletebook")
////		@ResponseBody
////		public String deleteBook(Model model, 
////				@RequestParam("bookId") Integer bookId,
////				@RequestParam("pageNo") Integer pageNo,
////				@RequestParam("searchString") String searchString) throws SQLException{
////			Book book = new Book();
////			book.setBookId(bookId);
////			adminBookService.deleteBook(book);
////			List<Book> books = adminBookService.getAllBooks(pageNo, searchString);
////			model.addAttribute("books", books);
////			return bookTable(books);
////		}
//	//	
//	//
//	////	
//////		@RequestMapping(value = "/deletebranches", method = RequestMethod.GET,produces="application/json")
//////		public List<Branch> editAuthors() throws SQLException {
//////			
//	////	
//////			List<Branch> lbrach=adminBranchService.getAllBranches(1, null);
//////			for(Branch b : lbrach) {
//////				
//////				b.setBookCopies(bcopies.deleteBookCopies(b.getBookCopies()));
//////				
//////			}
//////			
//////			return lbrach;
//////		}
//	////	
//	////	
//		
//		
//		
//	}

	
	
	
}
