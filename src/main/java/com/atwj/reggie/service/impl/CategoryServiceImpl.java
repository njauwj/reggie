package com.atwj.reggie.service.impl;

import com.atwj.reggie.common.CustomException;
import com.atwj.reggie.entity.Category;
import com.atwj.reggie.entity.Dish;
import com.atwj.reggie.entity.Setmeal;
import com.atwj.reggie.mapper.CategoryMapper;
import com.atwj.reggie.service.CategoryService;
import com.atwj.reggie.service.DishService;
import com.atwj.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: wj
 * @create_time: 2022/11/12 10:48
 * @explain:
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private DishService dishService;
    @Resource
    private SetmealService setmealService;


    @Override
    public void delCategory(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        if (count1 > 0) {
            throw new CustomException("有关联菜品，无法删除");
        }
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if (count2 > 0) {
            throw new CustomException("有关联套餐，无法删除");
        }
        super.removeById(id);
    }
}
