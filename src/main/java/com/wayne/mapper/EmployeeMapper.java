package com.wayne.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wayne.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: wayne-flowable
 * @description:
 * @author: wayne
 * @create: 2021-04-26 19:31
 **/
@Repository
public interface EmployeeMapper extends BaseMapper<Employee> {
    List<Employee> list();
}