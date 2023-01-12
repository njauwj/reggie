package com.atwj.reggie.controller;

import com.atwj.reggie.common.R;
import com.atwj.reggie.entity.Category;
import com.atwj.reggie.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: wj
 * @create_time: 2022/11/12 10:50
 * @explain: 分类管理
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;


    @PostMapping
    public R<String> addCategory(@RequestBody Category category) {
        categoryService.save(category);
        return R.success("添加分类成功");
    }

    @GetMapping("/page")
    public R<Page<Category>> getCategoryPage(int page, int pageSize) {
        Page<Category> categoryPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        categoryService.page(categoryPage, queryWrapper);
        return R.success(categoryPage);
    }

    @DeleteMapping
    public R<String> delCategory(Long id) {
        categoryService.delCategory(id);
        return R.success("移除成功");
    }

    @PutMapping
    public R<String> editCategory(@RequestBody Category category) {

        categoryService.updateById(category);
        return R.success("修改成功");
    }

    /**
     * 获取菜品分类
     * 使用实体通用性更强(没用 int type）
     *
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> getCategoryList(Category category) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType() != null, Category::getType, category.getType());
        //优先按照sort升序排，再按照更新时间降序排
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> categoryList = categoryService.list(queryWrapper);
        return R.success(categoryList);
    }
}
