package com.dominikyang.library.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dominikyang.library.commons.CommonFinalValues;
import com.dominikyang.library.dao.BookDao;
import com.dominikyang.library.entity.Book;
import com.dominikyang.library.entity.BookExample;
import com.dominikyang.library.service.BookService;
import com.dominikyang.library.utils.RedisUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public PageInfo<Book> getBooks(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Book> books = bookDao.selectByExample(new BookExample());
        PageInfo<Book> pageInfo = new PageInfo<>(books);
        return pageInfo;
    }

    @Override
    public Book getBook(int id) {
        Book existBook = (Book) redisTemplate.opsForValue().get(CommonFinalValues.BOOK_DETAILS + id);
        if (existBook == null) {
            Book book = bookDao.selectByPrimaryKey(id);
            if (book == null) {
                redisTemplate.opsForValue().set(CommonFinalValues.BOOK_DETAILS + id, new Book(id, -1),400);
                //访问不存在的请求，需要把这个异常写进日志里面
            }
            return book;
        } else if (existBook.getState() == -1) {
            //不存在的结果被缓存了起来
            //访问不存在的请求，需要把这个异常写进日志里面
            return null;
        } else {
            return existBook;
        }
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
        try {
            bookDao.insert(book);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean editBook(Book book) {
        try {
            bookDao.updateByPrimaryKey(book);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delBook(Integer id) {
        BookExample example = new BookExample();
        example.createCriteria().andIdEqualTo(id);
        try {
            bookDao.deleteByExample(example);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
