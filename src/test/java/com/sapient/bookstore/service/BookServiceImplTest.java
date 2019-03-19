package com.sapient.bookstore.service;

import com.sapient.bookstore.domain.Book;
import com.sapient.bookstore.domain.BookSearchOption;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BookServiceImplTest {

    @Autowired
    BookService bookService;
    @Test
    public void addBook() {
        Book book =new Book();
        book.setTitle("TestBookTitle");
        book.setListPrice(11);
        book.setAuthor("AuthorPankaj");
        book.setIsbn(12345678);
        bookService.addNewBook(book);
        BookSearchOption bookSearchOption=new BookSearchOption();
        bookSearchOption.setSearchBy("isbn");
        bookSearchOption.setSearchKeywords("12345678");
        List<Book> booksaved=(List<Book>)bookService.blurrySearch(bookSearchOption).getData();
        Assert.assertEquals(book.getIsbn(),booksaved.get(0).getIsbn());
    }
}
