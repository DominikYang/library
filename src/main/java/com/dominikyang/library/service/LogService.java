package com.dominikyang.library.service;

import com.dominikyang.library.entity.LogAdmin;
import com.dominikyang.library.entity.LogWarn;
import com.dominikyang.library.exception.GlobalException;

import java.util.List;

/**
 * 创建人：肖易安
 * 创建时间：  2020/7/1
 * 注释：null
 **/
public interface LogService {
    List<LogAdmin> logAdminList();

    List<LogWarn> logWarnList();

    void addLogAdmin(LogAdmin logAdmin) throws GlobalException;

    void addLogWarn(LogWarn logWarn) throws GlobalException;
}
