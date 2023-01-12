package com.atwj.reggie.service;

import com.atwj.reggie.entity.SetmealDish;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author: wj
 * @create_time: 2022/11/14 21:36
 * @explain:
 */

public interface SetmealDishService extends IService<SetmealDish> {

    List<SetmealDish> getSetmealDishList(String ids);

}
