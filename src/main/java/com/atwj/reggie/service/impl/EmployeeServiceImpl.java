package com.atwj.reggie.service.impl;

import com.atwj.reggie.entity.Employee;
import com.atwj.reggie.mapper.EmployeeMapper;
import com.atwj.reggie.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author: wj
 * @create_time: 2022/11/10 16:17
 * @explain:
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
