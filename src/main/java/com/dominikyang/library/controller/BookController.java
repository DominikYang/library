package com.dominikyang.library.controller;

import com.auth0.jwt.JWT;
import com.dominikyang.library.commons.CommonFinalValues;
import com.dominikyang.library.entity.Book;
import com.dominikyang.library.entity.User;
import com.dominikyang.library.exception.GlobalException;
import com.dominikyang.library.result.BaseResult;
import com.dominikyang.library.result.OperatorCode;
import com.dominikyang.library.service.BookService;
import com.dominikyang.library.service.LogService;
import com.dominikyang.library.service.OrderService;
import com.dominikyang.library.service.UserService;
import com.dominikyang.library.utils.VerificationTokenUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Wei yuyaung
 * @date 2020.06.23 23:25
 */
@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("/book")
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    private BookService bookService;
    private UserService userService;
    private OrderService orderService;
    private LogService logService;
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    public BookController(BookService bookService,UserService userService,OrderService orderService,LogService logService,RedisTemplate<String,Object> redisTemplate){
        this.bookService = bookService;
        this.userService = userService;
        this.orderService = orderService;
        this.logService = logService;
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("{pageNum}")
    public BaseResult<PageInfo<Book>> bookList(@PathVariable Integer pageNum){
        PageInfo<Book> books = bookService.getBooks(pageNum, CommonFinalValues.PAGE_SIZE);
        return BaseResult.success(books);
    }

    @PostMapping("details/{id}")
    public BaseResult<Book> bookDetails(@PathVariable Integer id, HttpServletRequest httpServletRequest) throws GlobalException {
        VerificationTokenUtils.redisToHeader(httpServletRequest, OperatorCode.SELECT_BOOK_DETAILS,logService,redisTemplate);
        Book book = bookService.getBook(id);
        return BaseResult.success(book);
    }

    @PostMapping("search")
    public BaseResult<List<Book>> searchList(String name, HttpServletRequest httpServletRequest){
        List<Book> books = bookService.searchBook(name);
        return BaseResult.success(books);
    }

    @PostMapping("borrow")
    public BaseResult<String> borrowBook(Integer id ,HttpServletRequest httpServletRequest) throws GlobalException {
        Book book = bookService.getBook(id);
        User user = userService.getUser(Integer.parseInt(JWT.decode(httpServletRequest.getHeader("token")).getAudience().get(0)));
        String orderId = orderService.createOrder(book,user);
        return BaseResult.success(orderId);
    }
}
