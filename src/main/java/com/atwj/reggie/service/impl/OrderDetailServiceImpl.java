package com.atwj.reggie.service.impl;

import com.atwj.reggie.entity.OrderDetail;
import com.atwj.reggie.mapper.OrderDetailMapper;
import com.atwj.reggie.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author: wj
 * @create_time: 2022/11/16 15:51
 * @explain:
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
