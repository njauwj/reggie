package com.atwj.reggie.controller;

import com.atwj.reggie.common.R;
import com.atwj.reggie.dto.DishDto;
import com.atwj.reggie.entity.Dish;
import com.atwj.reggie.entity.DishFlavor;
import com.atwj.reggie.entity.Setmeal;
import com.atwj.reggie.entity.SetmealDish;
import com.atwj.reggie.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: wj
 * @create_time: 2022/11/12 14:48
 * @explain: 菜品管理
 */

@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {
    @Resource
    private DishService dishService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private DishFlavorService dishFlavorService;

    @Resource
    private SetmealDishService setmealDishService;

    @Resource
    private SetmealService setmealService;

    @GetMapping("/page")
    public R<Page<DishDto>> getDishPage(int page, int pageSize, String name) {
        //分页构造器
        Page<Dish> dishPage = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<DishDto>();
        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.like(Dish::getName, name);
        }
        dishService.page(dishPage, queryWrapper);
        //对象拷贝,ignoreProperties设置不拷贝的值
        BeanUtils.copyProperties(dishPage, dishDtoPage, "records");
        List<DishDto> dishDtos = new ArrayList<>();
        List<Dish> dishes = dishPage.getRecords();
        for (Dish dish : dishes) {
            DishDto dishDto = new DishDto();
            //对象拷贝
            BeanUtils.copyProperties(dish, dishDto);
            Long categoryId = dish.getCategoryId();
            String categoryName = categoryService.getById(categoryId).getName();
            dishDto.setCategoryName(categoryName);
            dishDtos.add(dishDto);
        }
        dishDtoPage.setRecords(dishDtos);
        return R.success(dishDtoPage);

    }

    @PostMapping
    public R<String> addDish(@RequestBody DishDto dishDto) {
        dishService.saveDishAndFlavor(dishDto);
        return R.success("添加菜品成功");
    }

    @GetMapping("/{id}")
    public R<DishDto> queryDishById(@PathVariable("id") Long id) {
        DishDto dishDto = new DishDto();
        Dish dish = dishService.getById(id);
        BeanUtils.copyProperties(dish, dishDto);
        Long categoryId = dish.getCategoryId();
        String categoryName = categoryService.getById(categoryId).getName();
        dishDto.setCategoryName(categoryName);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, id);
        List<DishFlavor> dishFlavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(dishFlavors);
        return R.success(dishDto);
    }

    @PutMapping
    public R<String> editDish(@RequestBody DishDto dishDto) {
        dishService.updateAndFlavor(dishDto);
        return R.success("修改菜品成功");
    }

    @GetMapping("/list")// 通过套餐ID获取菜品列表分类
    public R<List<Dish>> queryDishList(Dish dish) {
        Long categoryId = dish.getCategoryId();
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getCategoryId, categoryId);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        queryWrapper.eq(Dish::getStatus, 1);
        List<Dish> dishList = dishService.list(queryWrapper);
        return R.success(dishList);
    }

    //批量删除
    @DeleteMapping
    public R<String> deleteDish(Long[] ids) {
        return dishService.deleteDishAndFlavor(ids);
    }

    //0停售或1起售
    /*
    当菜品有关联套餐时不能直接停售
    批量停售单个停售都是对应这个接口
     */
    @PostMapping("/status/{status}")
    public R<String> dishStatusByStatus(@PathVariable("status") Integer status, Long[] ids) {
        String s = Arrays.toString(ids);
        String charIds = s.substring(1, s.length() - 1);
        Dish dish = new Dish();
        if (status == 0) {
            ArrayList<Long> list = new ArrayList<>(Arrays.asList(ids));
            List<SetmealDish> setmealDishList = setmealDishService.getSetmealDishList(charIds);
            for (SetmealDish setmealDish : setmealDishList) {
                Long setmealId = setmealDish.getSetmealId();
                Setmeal setmeal = setmealService.getById(setmealId);
                Integer status1 = setmeal.getStatus();
                if (status1 == 1) {
                    return R.error("当前菜品有在售套餐无法停止销售");
                }
            }
            dish.setStatus(0);
            for (Long id : ids) {
                dish.setId(id);
                dishService.updateById(dish);
            }
        }
        if (status == 1) {
            dish.setStatus(1);
            for (Long id : ids) {
                dish.setId(id);
                dishService.updateById(dish);
            }
        }
        return R.success("状态修改成功");
    }

}
