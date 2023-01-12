package com.atwj.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 员工实体类
 */
@Data
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;//账号

    private String name;//员工姓名

    private String password;

    private String phone;

    private String sex;

    private String idNumber;

    //0禁用，1正常
    private Integer status;

    /**
     * 字段自动填充策略
     * 对公共字段进行自动填充，减少重复代码
     * 在对应模式下将会忽略 insertStrategy 或 updateStrategy 的配置,等于断言该字段必有值
     */

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

}
