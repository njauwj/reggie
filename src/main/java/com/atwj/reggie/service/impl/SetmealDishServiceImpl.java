package com.atwj.reggie.service.impl;

import com.atwj.reggie.entity.SetmealDish;
import com.atwj.reggie.mapper.SetmealDishMapper;
import com.atwj.reggie.mapper.SetmealMapper;
import com.atwj.reggie.service.SetmealDishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: wj
 * @create_time: 2022/11/14 21:36
 * @explain:
 */
@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {

    @Resource
    private SetmealDishMapper setmealDishMapper;

    @Override
    public List<SetmealDish> getSetmealDishList(String ids) {
        return setmealDishMapper.getSetmealDishByDishIds(ids);
    }
}
