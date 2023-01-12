package com.atwj.reggie.mapper;

import com.atwj.reggie.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: wj
 * @create_time: 2022/11/15 12:00
 * @explain:
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
