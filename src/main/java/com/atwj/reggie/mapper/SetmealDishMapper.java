package com.atwj.reggie.mapper;

import com.atwj.reggie.entity.SetmealDish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: wj
 * @create_time: 2022/11/14 21:35
 * @explain:
 */
@Mapper
public interface SetmealDishMapper extends BaseMapper<SetmealDish> {

    List<SetmealDish> getSetmealDishByDishIds(String  ids);

}
