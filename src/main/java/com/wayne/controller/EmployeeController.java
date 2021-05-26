package com.wayne.controller;

import com.wayne.common.api.ApiResult;
import com.wayne.entity.Employee;
import com.wayne.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @program: wayne_flowable
 * @description:
 * @author: wayne
 * @create: 2021-04-26 19:52
 **/
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/list")
    public ApiResult<List<Employee>> findAllEmployee() {
        return ApiResult.success(employeeService.list());
    }

    @GetMapping("/findById")
    public ApiResult<Employee> findById(@RequestParam Long id) {
        return ApiResult.success(employeeService.findById(id));
    }

    @PostMapping("/findByName")
    public ApiResult<List<Employee>> findByName(@RequestParam String name) {
        return ApiResult.success(employeeService.findByName(name));
    }


    @PostMapping("/add")
    public ApiResult<Employee> add(@Valid @RequestBody Employee employee) {
        Integer integer = employeeService.add(employee);
        if (integer == 0) {
            return ApiResult.failed("注册失败");
        } else {
            return ApiResult.success(employee);
        }
    }

    @PostMapping("/update")
    public ApiResult<Employee> update(@Valid @RequestBody Employee employee) {
        Integer integer = employeeService.update(employee);
        if (integer == 0) {
            return ApiResult.failed("更新失败");
        } else {
            return ApiResult.success(employee);
        }
    }

    @PostMapping("/delete")
    public ApiResult<String> delete(@RequestParam String id) {
        Integer integer = employeeService.delete(id);
        if (integer == 1) {
            return ApiResult.success("删除成功");
        } else {
            return ApiResult.failed("删除失败");
        }
    }
}
