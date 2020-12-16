package com.dominikyang.library.service;

import com.dominikyang.library.entity.Book;
import com.dominikyang.library.exception.GlobalException;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Wei yuyaung
 * @date 2020.06.23 23:32
 */
public interface BookService {

    PageInfo<Book> getBooks(int pageNum, int pageSize);

    Book getBook(int id);

    List<Book> searchBook(String name);

    boolean addBook(Book book) throws GlobalException;

    boolean editBook(Book book);

    boolean delBook(Integer id);
}
