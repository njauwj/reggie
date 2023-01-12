package com.atwj.reggie.service;

import com.atwj.reggie.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author: wj
 * @create_time: 2022/11/12 10:47
 * @explain:
 */
public interface CategoryService extends IService<Category> {

    public void delCategory(Long id);

}
