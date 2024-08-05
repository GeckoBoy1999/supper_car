package com.leder.super_car.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leder.super_car.mapper.AreaMapper;
import com.leder.super_car.pojo.po.Area;
import com.leder.super_car.pojo.vo.AreaVo;
import com.leder.super_car.service.AreaService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @className: AreaServiceImpl
 * @author: xiaomao
 * @date: 2024/7/29 20:15
 * @Version: 1.0
 * @description: 省市区实现类
 */

@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService{

    /**
     * @Description: 省市区查询
     * @Author: xiaomao
     * @Date: 2024/7/31 23:07
     * @param pid:
     * @return java.util.List<com.leder.super_car.pojo.vo.AreaVo>
     */
    @Override
    public List<AreaVo> listByPid(Long pid) {

        List<Area> areas = baseMapper.selectList(new LambdaQueryWrapper<Area>().eq(Area::getParentId, pid));

        return BeanUtil.copyToList(areas, AreaVo.class);
    }



    @Override
    public void removeAreaCacheByParentId(Long pid) {

    }


}
