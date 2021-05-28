package com.wayne.flowable.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author bin.wei
 * @Date 2021/5/26
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_employee")
public class Employee implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String sex;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date birthday;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date entryDate;
    @TableLogic
    private Integer deleted;
}
