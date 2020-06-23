package com.dominikyang.library.controller;

import com.dominikyang.library.commons.CommonFinalValues;
import com.dominikyang.library.entity.Book;
import com.dominikyang.library.result.BaseResult;
import com.dominikyang.library.service.BookService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Wei yuyaung
 * @date 2020.06.23 23:25
 */
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("{pageNum}")
    public BaseResult<PageInfo<Book>> bookList(@PathVariable Integer pageNum){
        PageInfo<Book> books = bookService.getBooks(pageNum, CommonFinalValues.PAGE_SIZE);
        return BaseResult.success(books);
    }

    @PostMapping("details")
    public BaseResult<Book> bookDetails(Integer id){
        Book book = bookService.getBook(id);
        return BaseResult.success(book);
    }

}
