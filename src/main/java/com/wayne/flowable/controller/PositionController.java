package com.wayne.flowable.controller;

import com.wayne.common.api.ApiResult;
import com.wayne.flowable.entity.Position;
import com.wayne.flowable.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @program: wayne_flowable
 * @description:
 * @author: wayne
 * @create: 2021-04-27 11:20
 **/
@RestController
@RequestMapping("/position")
public class PositionController {
    @Autowired
    private PositionService positionService;

    @GetMapping("/list")
    public ApiResult<List<Position>> list(){
        return ApiResult.success(positionService.list());
    }

    @PostMapping("/findByEmployeeId")
    public ApiResult<List<Position>> findByEmployeeId(@RequestParam Long employeeId){
        return ApiResult.success(positionService.findByEmployeeId(employeeId));
    }

    @PostMapping("/add")
    public ApiResult<Position> add(@Valid @RequestBody Position position){
        Integer integer = positionService.add(position);
        if (integer == 0) {
            return ApiResult.failed("注册失败");
        } else {
            return ApiResult.success(position);
        }
    }

    @PostMapping("/update")
    public ApiResult<Position> update(@Valid @RequestBody Position position){
        Integer integer = positionService.update(position);
        if (integer == 0) {
            return ApiResult.failed("更新失败");
        } else {
            return ApiResult.success(position);
        }
    }

    @PostMapping("/delete")
    public ApiResult<String>  delete(@Valid @RequestParam Long id){
        Integer integer = positionService.delete(id);
        if (integer == 1) {
            return ApiResult.success("删除成功");
        } else {
            return ApiResult.failed("删除失败");
        }
    }
}
