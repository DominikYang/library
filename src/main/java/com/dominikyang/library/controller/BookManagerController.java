package com.dominikyang.library.controller;

import com.auth0.jwt.JWT;
import com.dominikyang.library.entity.Book;
import com.dominikyang.library.entity.LogAdmin;
import com.dominikyang.library.entity.LogWarn;
import com.dominikyang.library.exception.GlobalException;
import com.dominikyang.library.result.BaseResult;
import com.dominikyang.library.result.CodeMessage;
import com.dominikyang.library.result.OperatorCode;
import com.dominikyang.library.service.AdminService;
import com.dominikyang.library.service.BookService;
import com.dominikyang.library.service.LogService;
import com.dominikyang.library.service.OrderService;
import com.dominikyang.library.utils.TokenDecodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 创建人：wyy
 * 创建时间：  2020/6/30
 * 注释：null
 **/
@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("/admin/book")
public class BookManagerController {
    private static final Logger log = LoggerFactory.getLogger(BookManagerController.class);

    @Autowired
    private BookService bookService;
    @Autowired
    private LogService logService;
    @Autowired
    private AdminService adminService;

    @PostMapping("/add")
    public BaseResult<String> addBook(@RequestBody Book book, HttpServletRequest httpServletRequest) throws GlobalException {
        String userId = TokenDecodeUtils.getUserId(httpServletRequest);
        if (!adminService.isAdmin(Integer.parseInt(userId))) {
            return BaseResult.fail(CodeMessage.NOT_MANAGER);
        }
        boolean success = bookService.addBook(book);
        if (success) {
            LogAdmin logAdmin = new LogAdmin();
            logAdmin.setDetails("用户" + userId + " 添加" + book.getName());
            logAdmin.setOperateUserId(Integer.parseInt(userId));
            logAdmin.setOperateCode(OperatorCode.ADD_BOOK.getCode());
            logAdmin.setOperateName(OperatorCode.ADD_BOOK.getName());
            logAdmin.setTime(new Date());
            logService.addLogAdmin(logAdmin);
            log.info("用户" + userId + " 添加书籍：" + book.getName());
            return BaseResult.success("添加成功");
        } else {
            LogWarn logWarn = new LogWarn();
            logWarn.setTime(new Date());
            logWarn.setWarnCode(CodeMessage.ADD_BOOK_FAILE.getCode() + "");
            logWarn.setWarnName("添加书籍错误");
            logWarn.setDetails(CodeMessage.ADD_BOOK_FAILE.getMessage());
            logService.addLogWarn(logWarn);
            log.warn("添加书籍失败");
            return BaseResult.fail(CodeMessage.ADD_BOOK_FAILE);
        }
    }

    @PostMapping("/edit")
    public BaseResult<String> editBook(@RequestBody Book book, HttpServletRequest httpServletRequest) throws GlobalException {
        String userId;
        userId = TokenDecodeUtils.getUserId(httpServletRequest);
        if (!adminService.isAdmin(Integer.parseInt(userId))) {
            return BaseResult.fail(CodeMessage.NOT_MANAGER);
        }
        boolean success = bookService.editBook(book);
        if (success) {
            LogAdmin logAdmin = new LogAdmin();
            logAdmin.setDetails("用户" + userId + " 编辑" + book.getName());
            logAdmin.setOperateUserId(Integer.parseInt(userId));
            logAdmin.setOperateCode(OperatorCode.EDIT_BOOK.getCode());
            logAdmin.setOperateName(OperatorCode.EDIT_BOOK.getName());
            logAdmin.setTime(new Date());
            logService.addLogAdmin(logAdmin);
            log.info("用户" + userId + " 修改书籍" + book.getName());
            return BaseResult.success("编辑成功");
        } else {
            LogWarn logWarn = new LogWarn();
            logWarn.setTime(new Date());
            logWarn.setWarnCode(CodeMessage.EDIT_BOOK_FAILE.getCode() + "");
            logWarn.setWarnName("编辑书籍错误");
            logWarn.setDetails(CodeMessage.EDIT_BOOK_FAILE.getMessage());
            logService.addLogWarn(logWarn);
            log.warn("编辑书籍失败");
            return BaseResult.fail(CodeMessage.EDIT_BOOK_FAILE);
        }
    }

    @PostMapping("/del/{id}")
    public BaseResult<String> delBook(@PathVariable Integer id, HttpServletRequest httpServletRequest) throws GlobalException {
        String userId = TokenDecodeUtils.getUserId(httpServletRequest);
        if (!adminService.isAdmin(Integer.parseInt(userId))) {
            return BaseResult.fail(CodeMessage.NOT_MANAGER);
        }
        boolean success = bookService.delBook(id);
        if (success) {
            LogAdmin logAdmin = new LogAdmin();
            logAdmin.setDetails("删除书籍ID:" + id);
            logAdmin.setOperateUserId(Integer.parseInt(userId));
            logAdmin.setOperateName(OperatorCode.DEL_BOOK.getName());
            logAdmin.setOperateCode(OperatorCode.DEL_BOOK.getCode());
            logAdmin.setTime(new Date());
            logService.addLogAdmin(logAdmin);
            log.info("用户" + userId + " 删除书籍:" + id);
            return BaseResult.success("删除成功");
        } else {
            LogWarn logWarn = new LogWarn();
            logWarn.setTime(new Date());
            logWarn.setWarnCode(CodeMessage.DEL_BOOK_FAILE.getCode() + "");
            logWarn.setWarnName("删除书籍错误");
            logWarn.setDetails(CodeMessage.DEL_BOOK_FAILE.getMessage());
            logService.addLogWarn(logWarn);
            log.warn("删除书籍失败");
            return BaseResult.fail(CodeMessage.DEL_BOOK_FAILE);
        }
    }
}
