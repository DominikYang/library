package com.dominikyang.library.controller;

import com.dominikyang.library.entity.LogWarn;
import com.dominikyang.library.exception.GlobalException;
import com.dominikyang.library.result.BaseResult;
import com.dominikyang.library.result.CodeMessage;
import com.dominikyang.library.service.LogService;
import com.dominikyang.library.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 创建人：肖易安
 * 创建时间：  2020/6/30
 * 注释：null
 **/
@RestController
@RequestMapping("/admin/order")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private LogService logService;

    @PostMapping("/state")
    public BaseResult<String> orderState(Integer id, Integer state, HttpServletRequest httpServletRequest) throws GlobalException {
        boolean success = orderService.returnOrder(id,state);
        if(success){
            return BaseResult.success("修改成功");
        }else{
            LogWarn logWarn = new LogWarn();
            logWarn.setTime(new Date());
            logWarn.setWarnCode(CodeMessage.CHANGE_STATE_ERROR.getCode()+"");
            logWarn.setWarnName("修改订单错误");
            logWarn.setDetails(CodeMessage.CHANGE_STATE_ERROR.getMessage());
            logService.addLogWarn(logWarn);
            log.warn("修改订单状态失败");
            return BaseResult.fail(CodeMessage.CHANGE_STATE_ERROR);
        }
    }
}
