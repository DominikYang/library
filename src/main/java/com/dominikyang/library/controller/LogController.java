package com.dominikyang.library.controller;

import com.auth0.jwt.JWT;
import com.dominikyang.library.commons.CommonFinalValues;
import com.dominikyang.library.entity.Book;
import com.dominikyang.library.entity.LogAdmin;
import com.dominikyang.library.entity.LogWarn;
import com.dominikyang.library.exception.GlobalException;
import com.dominikyang.library.result.BaseResult;
import com.dominikyang.library.result.CodeMessage;
import com.dominikyang.library.service.*;
import com.dominikyang.library.utils.TokenDecodeUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 创建人：肖易安
 * 创建时间：  2020/7/1
 * 注释：null
 **/
@CrossOrigin(value = "*")
@RestController
@RequestMapping("/admin/log")
public class LogController {
    private static final Logger log = LoggerFactory.getLogger(LogController.class);

    private LogService logService;
    private AdminService adminService;

    @Autowired
    public LogController(LogService logService,AdminService adminService){
        this.adminService = adminService;
        this.logService = logService;
    }

    @GetMapping("operate")
    public BaseResult<List<LogAdmin>> logAdminList(HttpServletRequest httpServletRequest) throws GlobalException {
        String userId;
        try{
            userId = TokenDecodeUtils.getUserId(httpServletRequest);
        }catch (GlobalException e){
            return BaseResult.fail(e.getCodeMessage());
        }
        if(!adminService.isAdmin(Integer.parseInt(userId))){
            return BaseResult.fail(CodeMessage.NOT_MANAGER);
        }
        List<LogAdmin> logAdmins = logService.logAdminList();
        log.info("用户" + userId + " 查看管理员操作日志");
        return BaseResult.success(logAdmins);
    }

    @GetMapping("warn")
    public BaseResult<List<LogWarn>> logWarnList(HttpServletRequest httpServletRequest) throws GlobalException {
        String userId;
        try{
            userId = TokenDecodeUtils.getUserId(httpServletRequest);
        }catch (GlobalException e){
            return BaseResult.fail(e.getCodeMessage());
        }
        if(!adminService.isAdmin(Integer.parseInt(userId))){
            return BaseResult.fail(CodeMessage.NOT_MANAGER);
        }
        List<LogWarn> logWarns = logService.logWarnList();
        log.info("用户" + userId + " 查看错误日志");
        return BaseResult.success(logWarns);
    }
}
