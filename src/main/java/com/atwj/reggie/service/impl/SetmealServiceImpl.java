package com.atwj.reggie.service.impl;

import com.atwj.reggie.dto.SetmealDto;
import com.atwj.reggie.entity.Dish;
import com.atwj.reggie.entity.Setmeal;
import com.atwj.reggie.entity.SetmealDish;
import com.atwj.reggie.mapper.SetmealMapper;
import com.atwj.reggie.service.DishService;
import com.atwj.reggie.service.SetmealDishService;
import com.atwj.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: wj
 * @create_time: 2022/11/12 16:11
 * @explain:
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Resource
    private SetmealDishService setmealDishService;

    @Override
    @Transactional
    public void addSetmealaAndDish(SetmealDto setmealDto) {//目标将setmealId传进去
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        this.save(setmealDto);
        Long setmealId = setmealDto.getId();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealId);
        }
        setmealDishService.saveBatch(setmealDishes);

    }

    @Override
    @Transactional
    public void deleteSetmealAndDish(Long[] ids) {
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        for (Long id : ids) {
            this.removeById(id);
            queryWrapper.eq(SetmealDish::getSetmealId,id);
            setmealDishService.remove(queryWrapper);
        }
    }

    @Override
    @Transactional
    public void updateSetmeal(SetmealDto setmealDto) {
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,setmealDto.getId());
        setmealDishService.remove(queryWrapper);
        this.updateById(setmealDto);
        List<SetmealDish> setmealDishList = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishList) {
            setmealDish.setSetmealId(setmealDto.getId());
        }
        setmealDishService.saveBatch(setmealDishList);
    }
}
