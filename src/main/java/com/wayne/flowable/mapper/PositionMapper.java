package com.wayne.flowable.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wayne.flowable.entity.Position;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: wayne_flowable
 * @description:
 * @author: wayne
 * @create: 2021-04-27 11:02
 **/
@Repository
public interface PositionMapper extends BaseMapper<Position> {
    List<Position> list();
}