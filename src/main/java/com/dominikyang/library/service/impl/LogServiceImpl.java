package com.dominikyang.library.service.impl;

import com.dominikyang.library.dao.LogAdminDao;
import com.dominikyang.library.dao.LogWarnDao;
import com.dominikyang.library.entity.LogAdmin;
import com.dominikyang.library.entity.LogAdminExample;
import com.dominikyang.library.entity.LogWarn;
import com.dominikyang.library.entity.LogWarnExample;
import com.dominikyang.library.exception.GlobalException;
import com.dominikyang.library.result.CodeMessage;
import com.dominikyang.library.service.LogService;
import jdk.nashorn.internal.objects.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 创建人：肖易安
 * 创建时间：  2020/7/1
 * 注释：null
 **/
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogAdminDao logAdminDao;
    @Autowired
    private LogWarnDao logWarnDao;

    @Override
    public List<LogAdmin> logAdminList() {
        LogAdminExample example = new LogAdminExample();
        example.createCriteria().andIdIsNotNull();
        List<LogAdmin> logAdmins = logAdminDao.selectByExample(example);
        return logAdmins;
    }

    @Override
    public List<LogWarn> logWarnList() {
        LogWarnExample example = new LogWarnExample();
        example.createCriteria().andIdIsNotNull();
        List<LogWarn> logWarns = logWarnDao.selectByExample(example);
        return logWarns;
    }

    @Override
    public void addLogAdmin(LogAdmin logAdmin) throws GlobalException {
        try{
            logAdminDao.insert(logAdmin);
        }catch (Exception e){
            throw new GlobalException(CodeMessage.ADD_LOG_ERROR);
        }
    }

    @Override
    public void addLogWarn(LogWarn logWarn) throws GlobalException {
        try{
            logWarnDao.insert(logWarn);
        }catch (Exception e){
            throw new GlobalException(CodeMessage.ADD_LOG_ERROR);
        }
    }
}
