package com.wayne.flowable.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wayne.flowable.entity.Employee;
import com.wayne.flowable.mapper.EmployeeMapper;
import com.wayne.flowable.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: wayne_flowable
 * @description:
 * @author: wayne
 * @create: 2021-04-26 19:46
 **/
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Override
    public List<Employee> list() {
        return employeeMapper.list();
    }

    @Override
    public Employee findById(Long id) {
        return employeeMapper.selectById(id);
    }

    @Override
    public Integer add(Employee employee) {
        return employeeMapper.insert(employee);
    }

    @Override
    public Integer update(Employee employee) {
        return employeeMapper.updateById(employee);
    }

    @Override
    public List<Employee> findByName(String employeeName) {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("name",employeeName);
        return employeeMapper.selectList(queryWrapper);
    }

    @Override
    public Integer delete(String employeeId) {
        return employeeMapper.deleteById(employeeId);
    }
}
