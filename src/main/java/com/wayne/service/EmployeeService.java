package com.wayne.service;


import com.wayne.entity.Employee;

import java.util.List;

/**
 * @program: wayne_flowable
 * @description:
 * @author: wayne
 * @create: 2021-04-26 19:45
 **/
public interface EmployeeService {
    List<Employee> list();

    Employee findById(Long id);

    Integer add(Employee employee);

    Integer update(Employee employee);

    List<Employee> findByName(String employeeName);

    Integer delete(String employeeId);

}
