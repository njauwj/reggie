package com.atwj.reggie.mapper;

import com.atwj.reggie.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: wj
 * @create_time: 2022/11/10 16:14
 * @explain:
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
