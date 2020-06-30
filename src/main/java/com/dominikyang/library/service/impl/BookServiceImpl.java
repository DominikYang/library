package com.dominikyang.library.service.impl;

import com.dominikyang.library.dao.BookDao;
import com.dominikyang.library.entity.Book;
import com.dominikyang.library.entity.BookExample;
import com.dominikyang.library.service.BookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wei yuyaung
 * @date 2020.06.23 23:34
 */
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    @Override
    public PageInfo<Book> getBooks(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Book> books = bookDao.selectByExample(new BookExample());
        PageInfo<Book> pageInfo = new PageInfo<>(books);
        return pageInfo;
    }

    @Override
    public Book getBook(int id) {
        return bookDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Book> searchBook(String name) {
        BookExample example = new BookExample();
        example.createCriteria().andAuthorLike(name);
        example.or(example.createCriteria().andNameLike(name));
        List<Book> books = bookDao.selectByExample(example);
        return books;
    }

    @Override
    public boolean addBook(Book book) {
        int success = bookDao.insert(book);
        if(success<1){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public boolean editBook(Book book) {
        int success = bookDao.updateByPrimaryKey(book);
        if(success<1){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public boolean delBook(Integer id) {
        BookExample example = new BookExample();
        example.createCriteria().andIdEqualTo(id);
        int success = bookDao.deleteByExample(example);
        if(success<1){
            return false;
        }else{
            return true;
        }
    }

}
