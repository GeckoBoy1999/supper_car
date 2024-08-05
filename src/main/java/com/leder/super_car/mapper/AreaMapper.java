package com.leder.super_car.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.leder.super_car.pojo.po.Area;
import org.apache.ibatis.annotations.Mapper;


/**
 * @interfaceName: AreaMapper
 * @author: xiaomao
 * @date: 2024/7/29 20:42
 * @Version: 1.0
 * @description: 省市区地址持久层
 */


@Mapper
public interface AreaMapper extends MPJBaseMapper<Area> {
}