package com.atwj.reggie.service.impl;

import com.atwj.reggie.common.R;
import com.atwj.reggie.dto.DishDto;
import com.atwj.reggie.entity.Dish;
import com.atwj.reggie.entity.DishFlavor;
import com.atwj.reggie.entity.SetmealDish;
import com.atwj.reggie.mapper.DishMapper;
import com.atwj.reggie.service.CategoryService;
import com.atwj.reggie.service.DishFlavorService;
import com.atwj.reggie.service.DishService;
import com.atwj.reggie.service.SetmealDishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: wj
 * @create_time: 2022/11/12 14:47
 * @explain:
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Resource
    private DishFlavorService dishFlavorService;

    @Resource
    private DishService dishService;

    @Resource
    private SetmealDishService setmealDishService;


    @Override
    @Transactional//多表操作要加事务管理
    public void saveDishAndFlavor(DishDto dishDto) {
        this.save(dishDto);
        Long dishId = dishDto.getId();
        List<DishFlavor> dishFlavors = dishDto.getFlavors();
        for (DishFlavor dishFlavor : dishFlavors) {
            dishFlavor.setDishId(dishId);
            dishFlavorService.save(dishFlavor);
        }
    }

    @Override
    @Transactional
    public void updateAndFlavor(DishDto dishDto) {
        List<DishFlavor> flavors = dishDto.getFlavors();
        Long dishId = dishDto.getId();
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishId);
        //移除所有的dish口味
        dishFlavorService.remove(queryWrapper);
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishId);
        }
        //添加新的口味
        dishFlavorService.saveBatch(flavors);
        //更新菜品
        dishService.updateById(dishDto);
    }

    /**
     * 删除菜品的同时要删除口味，并且只有但套餐没有该菜品时才能删除
     *
     * @param ids
     */
    @Override
    @Transactional
    public R<String> deleteDishAndFlavor(Long[] ids) {
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        for (Long id : ids) {
            setmealDishLambdaQueryWrapper.eq(SetmealDish::getDishId,id);
            SetmealDish setmealDish = setmealDishService.getOne(setmealDishLambdaQueryWrapper);
            if(setmealDish != null) {
                return R.error("有关联套餐删除失败");
            }
            this.removeById(id);
            queryWrapper.eq(DishFlavor::getDishId, id);
            dishFlavorService.remove(queryWrapper);
        }
        return R.success("删除成功");
    }


}
