package com.atwj.reggie.controller;

import com.atwj.reggie.common.BaseContext;
import com.atwj.reggie.common.R;
import com.atwj.reggie.entity.Dish;
import com.atwj.reggie.entity.ShoppingCart;
import com.atwj.reggie.service.DishService;
import com.atwj.reggie.service.ShoppingCartService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author: wj
 * @create_time: 2022/11/16 16:28
 * @explain:
 */
@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {

    @Resource
    private ShoppingCartService shoppingCartService;


    @GetMapping("/list")
    public R<List<ShoppingCart>> cartListApi() {

        List<ShoppingCart> shoppingCartList = shoppingCartService.list();

        return R.success(shoppingCartList);
    }

    @PostMapping("/add")
    public R<String> addCartApi(@RequestBody ShoppingCart shoppingCart) {
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
        shoppingCartService.save(shoppingCart);
        return R.success("添加成功");
    }

    @PostMapping("/sub")
    public R<String> updateCartApi(@RequestBody ShoppingCart shoppingCart) {
        log.info("购物车中修改商品");
        return null;
    }

    @DeleteMapping("/clean")
    public R<String> clearCartApi() {
        log.info("删除购物车的商品");
        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,currentId);
        shoppingCartService.remove(queryWrapper);
        return R.success("清楚购物车成功");
    }
}
