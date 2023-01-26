package com.atwj.reggie.controller;

import com.atwj.reggie.common.R;
import com.atwj.reggie.dto.SetmealDto;
import com.atwj.reggie.entity.Dish;
import com.atwj.reggie.entity.Setmeal;
import com.atwj.reggie.entity.SetmealDish;
import com.atwj.reggie.service.CategoryService;
import com.atwj.reggie.service.SetmealDishService;
import com.atwj.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: wj
 * @create_time: 2022/11/12 16:12
 * @explain: 套餐管理
 */
@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {

    @Resource
    private SetmealService setMealService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private SetmealDishService setmealDishService;

    @GetMapping("/page")
    public R<Page<SetmealDto>> getSetmealPage(int page, int pageSize, String name) {
        Page<Setmeal> setMealPage = new Page<>(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        //注意判断name是否为空
        queryWrapper.like(StringUtils.isNotBlank(name), Setmeal::getName, name);
        setMealService.page(setMealPage, queryWrapper);
        BeanUtils.copyProperties(setMealPage, setmealDtoPage, "records");
        List<Setmeal> setmealList = setMealPage.getRecords();
        List<SetmealDto> setmealDtoList = new ArrayList<>();
        for (Setmeal setmeal : setmealList) {
            SetmealDto setmealDto = new SetmealDto();
            String categoryName = categoryService.getById(setmeal.getCategoryId()).getName();
            BeanUtils.copyProperties(setmeal, setmealDto);
            setmealDto.setCategoryName(categoryName);
            setmealDtoList.add(setmealDto);
        }
        setmealDtoPage.setRecords(setmealDtoList);
        return R.success(setmealDtoPage);
    }

    @PostMapping
    public R<String> addSetmeal(@RequestBody SetmealDto setmealDto) {
        setMealService.addSetmealaAndDish(setmealDto);
        return R.success("保存套餐成功");
    }

    @DeleteMapping
    public R<String> deleteSetmeal(Long[] ids) {
        setMealService.deleteSetmealAndDish(ids);
        return R.success("删除套餐成功");
    }

    @GetMapping("/{id}")
    public R<SetmealDto> querySetmealById(@PathVariable("id") Long id) {
        Setmeal setmeal = setMealService.getById(id);
        Long categoryId = setmeal.getCategoryId();
        String categoryName = categoryService.getById(categoryId).getName();
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, id);
        List<SetmealDish> setmealDishList = setmealDishService.list(queryWrapper);
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal, setmealDto);
        setmealDto.setCategoryName(categoryName);
        setmealDto.setSetmealDishes(setmealDishList);
        return R.success(setmealDto);
    }

    @PutMapping
    public R<String> editSetmeal(@RequestBody SetmealDto setmealDto) {
        setMealService.updateSetmeal(setmealDto);
        return R.success("编辑套餐成功");
    }

    @GetMapping("/list")
    public R<List<Setmeal>> setmealListApi(Setmeal setmeal) {
        log.info("获取菜品分类对应的套餐");
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        if (setmeal != null) {
            queryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
            queryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        }
        List<Setmeal> setmealList = setMealService.list(queryWrapper);
        return R.success(setmealList);
    }

    @GetMapping("/dish/{id}")
    public R<List<SetmealDish>> setMealDishDetailsApi(@PathVariable("id") Long id) {
        log.info("获取套餐的所有菜品");
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, id);
        List<SetmealDish> setmealDishList = setmealDishService.list(queryWrapper);
        return R.success(setmealDishList);
    }

    /*
    批量停售起售,0停用，1启用
     */
    @PostMapping("/status/{status}")
    public R<String> setmealStatusByStatus(@PathVariable("status") Integer status,Long[] ids) {
        Setmeal setmeal = new Setmeal();
        if(status == 0) {
            setmeal.setStatus(0);
            for (Long id : ids) {
                LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
                setmealLambdaQueryWrapper.eq(Setmeal::getId, id);
                setmeal.setId(id);
                setMealService.updateById(setmeal);
            }
            return R.success("停售成功");
        } else {
            setmeal.setStatus(1);
            for (Long id : ids) {
                LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
                setmealLambdaQueryWrapper.eq(Setmeal::getId, id);
                setmeal.setId(id);
                setMealService.updateById(setmeal);
            }
            return R.success("起售成功");
        }
    }

}
