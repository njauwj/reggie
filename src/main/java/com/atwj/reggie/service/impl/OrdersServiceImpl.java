package com.atwj.reggie.service.impl;

import com.atwj.reggie.entity.Orders;
import com.atwj.reggie.mapper.OrdersMapper;
import com.atwj.reggie.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author: wj
 * @create_time: 2022/11/16 15:46
 * @explain:
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
}
