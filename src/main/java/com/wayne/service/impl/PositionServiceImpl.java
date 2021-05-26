package com.wayne.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wayne.entity.Position;
import com.wayne.mapper.PositionMapper;
import com.wayne.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: wayne_flowable
 * @description:
 * @author: wayne
 * @create: 2021-04-27 11:07
 **/
@Service
public class PositionServiceImpl implements PositionService {
    @Autowired
    private PositionMapper positionMapper;

    @Override
    public List<Position> list() {
        return positionMapper.list();
    }

    @Override
    public List<Position> findByEmployeeId(Long id) {
        QueryWrapper<Position> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("employee_id",id);
        return positionMapper.selectList(queryWrapper);
    }

    @Override
    public Integer add(Position position) {
        return positionMapper.insert(position);
    }

    @Override
    public Integer update(Position position) {
        return positionMapper.updateById(position);
    }

    @Override
    public Integer delete(Long id) {
        return positionMapper.deleteById(id);
    }
}
