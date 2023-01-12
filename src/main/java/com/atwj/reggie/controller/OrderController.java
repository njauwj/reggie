package com.atwj.reggie.controller;

import com.atwj.reggie.common.BaseContext;
import com.atwj.reggie.common.R;
import com.atwj.reggie.entity.Orders;
import com.atwj.reggie.service.OrdersService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: wj
 * @create_time: 2022/11/16 15:52
 * @explain:
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Resource
    private OrdersService ordersService;

    @GetMapping("/page")
    public R<List<Orders>> getOrderDetailPage(int page, int pageSize, String number, String beginTime, String endTime) {
        Page<Orders> ordersPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(number), Orders::getNumber, number);
        queryWrapper.between(StringUtils.isNoneBlank(beginTime, endTime), Orders::getCheckoutTime, beginTime, endTime);
        List<Orders> ordersList = ordersService.list(queryWrapper);
        return R.success(ordersList);
    }

    @PostMapping("/submit")
    public R<String> addOrderApi(@RequestBody Orders orders) {
        log.info("提交订单");
        orders.setUserId(BaseContext.getCurrentId());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setOrderTime(LocalDateTime.now());
        ordersService.save(orders);
        return R.success("下单成功");
    }

}
