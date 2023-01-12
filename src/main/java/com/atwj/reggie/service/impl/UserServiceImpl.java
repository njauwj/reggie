package com.atwj.reggie.service.impl;

import com.atwj.reggie.entity.User;
import com.atwj.reggie.mapper.UserMapper;
import com.atwj.reggie.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author: wj
 * @create_time: 2022/11/15 12:58
 * @explain:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
