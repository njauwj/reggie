package com.atwj.reggie.service;

import com.atwj.reggie.common.R;
import com.atwj.reggie.dto.DishDto;
import com.atwj.reggie.entity.Dish;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author: wj
 * @create_time: 2022/11/12 14:47
 * @explain:
 */
public interface DishService extends IService<Dish> {

    void saveDishAndFlavor(DishDto dishDto);


    void updateAndFlavor(DishDto dishDto);

    R<String> deleteDishAndFlavor(Long[] ids);
}
