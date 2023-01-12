package com.atwj.reggie.service;

import com.atwj.reggie.dto.SetmealDto;
import com.atwj.reggie.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author: wj
 * @create_time: 2022/11/12 16:10
 * @explain:
 */
public interface SetmealService extends IService<Setmeal> {

    void addSetmealaAndDish(SetmealDto setmealDto);

    void deleteSetmealAndDish(Long[] ids);

    void updateSetmeal(SetmealDto setmealDto);

}
