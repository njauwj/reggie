package com.atwj.reggie.service.impl;

import com.atwj.reggie.entity.ShoppingCart;
import com.atwj.reggie.mapper.ShoppingCartMapper;
import com.atwj.reggie.service.ShoppingCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author: wj
 * @create_time: 2022/11/16 16:26
 * @explain:
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
