package com.wayne.service;

import com.wayne.entity.Position;

import java.util.List;

/**
 * @program: wayne_flowable
 * @description:
 * @author: wayne
 * @create: 2021-04-27 11:03
 **/
public interface PositionService {

    List<Position> list();

    List<Position> findByEmployeeId(Long id);

    Integer add(Position position);

    Integer update(Position position);

    Integer delete(Long id);
}
