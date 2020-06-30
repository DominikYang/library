package com.dominikyang.library.controller;

import com.dominikyang.library.result.BaseResult;
import com.dominikyang.library.result.CodeMessage;
import com.dominikyang.library.service.OrderService;
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
@RequestMapping("/admin/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/state")
    public BaseResult<String> orderState(Integer id, Integer state, HttpServletRequest httpServletRequest){
        boolean success = orderService.returnOrder(id,state);
        if(success){
            return BaseResult.success("修改成功");
        }else{
            return BaseResult.fail(CodeMessage.CHANGE_STATE_ERROR);
        }
    }
}
