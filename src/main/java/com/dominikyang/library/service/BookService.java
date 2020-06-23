package com.dominikyang.library.service;

import com.dominikyang.library.entity.Book;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Wei yuyaung
 * @date 2020.06.23 23:32
 */
public interface BookService {

    PageInfo<Book> getBooks(int pageNum, int pageSize);

    Book getBook(int id);
}