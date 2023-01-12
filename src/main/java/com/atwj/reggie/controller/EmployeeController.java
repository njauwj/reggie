package com.atwj.reggie.controller;

import com.atwj.reggie.common.R;
import com.atwj.reggie.entity.Employee;
import com.atwj.reggie.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: wj
 * @create_time: 2022/11/10 16:24
 * @explain: 员工管理
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    /*
    后台用户登入
     */
    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee, HttpServletRequest request) {//前端发送的是json格式的数据需要加@RequestBody来接受

        if (StringUtils.isAnyEmpty(employee.getUsername(), employee.getPassword())) {
            return R.error("账号或密码不能为空");
        }
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee loginEmp = employeeService.getOne(queryWrapper);
        if (loginEmp == null) {
            return R.error("用户不存在");
        }
        String password = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());//对密码进行加密处理

        if (!password.equals(loginEmp.getPassword())) {
            return R.error("密码错误");
        }
        if (loginEmp.getStatus() == 0) {//0表示账号被禁用
            return R.error("账号已被禁用");
        }
        request.getSession().setAttribute("loginEmpId", loginEmp.getId());

        return R.success(loginEmp);
    }

    /*
    用户退出
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("loginEmpId");
        return R.success("退出成功");
    }
    /*
    添加员工
     */
    @PostMapping
    public R<String> addEmployee(@RequestBody Employee employee) {
        log.info("新增员工，员工信息为：{}", employee);
        //初始化密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employeeService.save(employee);
        return R.success("创建成功");
    }


    /*
    员工分页查询
     */
    @GetMapping("/page")
    public R<Page<Employee>> pageQuery(int page, int pageSize, String name) {
        //分页构造器
        Page<Employee> employeePage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //过滤条件
        queryWrapper.like(StringUtils.isNoneBlank(name), Employee::getName, name);
        //排序
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        employeeService.page(employeePage, queryWrapper);
        return R.success(employeePage);
    }

    /*
    更新员工信息
     */
    @PutMapping
    public R<String> updateEmployee(@RequestBody Employee employee) {
        employeeService.updateById(employee);
        return R.success("更新成功");
    }

    /*
    点击编辑员工信息前，需进行一次查询
     */
    @GetMapping("/{id}")
    public R<Employee> queryEmployeeById(@PathVariable("id") Long id) {
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("未查询到结果");
    }

}
