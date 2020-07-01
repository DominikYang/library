package com.dominikyang.library.controller;

import com.auth0.jwt.JWT;
import com.dominikyang.library.entity.Book;
import com.dominikyang.library.result.BaseResult;
import com.dominikyang.library.result.CodeMessage;
import com.dominikyang.library.service.BookService;
import com.dominikyang.library.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 创建人：肖易安
 * 创建时间：  2020/6/30
 * 注释：null
 **/
@RestController
@RequestMapping("/admin/book")
public class BookManagerController {
    private static final Logger log = LoggerFactory.getLogger(BookManagerController.class);

    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public BaseResult<String> addBook(Book book, HttpServletRequest httpServletRequest) {
        String userid = JWT.decode(httpServletRequest.getHeader("token")).getAudience().get(0);
        boolean success = bookService.addBook(book);
        if (success) {
            log.info("用户"+userid+" 添加书籍："+book.getName());
            return BaseResult.success("添加成功");
        } else {
            log.warn("添加书籍失败");
            return BaseResult.fail(CodeMessage.ADD_BOOK_FAILE);
        }
    }

    @PostMapping("/edit")
    public BaseResult<String> editBook(Book book, HttpServletRequest httpServletRequest) {
        String userid = JWT.decode(httpServletRequest.getHeader("token")).getAudience().get(0);
        boolean success = bookService.editBook(book);
        if (success) {
            log.info("用户"+userid+" 修改书籍"+book.getName());
            return BaseResult.success("编辑成功");
        } else {
            log.warn("编辑书籍失败");
            return BaseResult.fail(CodeMessage.EDIT_BOOK_FAILE);
        }
    }

    @PostMapping("/del")
    public BaseResult<String> delBook(Integer id, HttpServletRequest httpServletRequest) {
        String userid = JWT.decode(httpServletRequest.getHeader("token")).getAudience().get(0);
        boolean success = bookService.delBook(id);
        if (success) {
            log.info("用户"+userid+" 删除书籍:"+id);
            return BaseResult.success("删除成功");
        } else {
            log.warn("删除书籍失败");
            return BaseResult.fail(CodeMessage.DEL_BOOK_FAILE);
        }
    }
}
