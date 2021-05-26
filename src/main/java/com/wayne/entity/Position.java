package com.wayne.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: wayne_flowable
 * @description: 员工职级
 * @author: wayne
 * @create: 2021-04-27 10:44
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_position")
public class Position implements Serializable {
    private Long id;
    private Long employeeId;
    private Float salary;

    @TableField("`rank`")
    private Integer rank;
    private Long leaderId;

    @TableLogic
    private Integer deleted;
}