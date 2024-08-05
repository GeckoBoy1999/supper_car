package com.leder.super_car.service;

import com.leder.super_car.pojo.po.Area;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leder.super_car.pojo.vo.AreaVo;

import java.util.List;


/**
 * @className: AreaService
 * @author: xiaomao
 * @date: 2024/7/29 20:15
 * @Version: 1.0
 * @description: 省市区接口
 */

public interface AreaService extends IService<Area>{


    /**
     * @Description: 省市区查询
     * @Author: xiaomao
     * @Date: 2024/7/31 23:07
     * @param pid:
     * @return java.util.List<com.leder.super_car.pojo.vo.AreaVo>
     */
    List<AreaVo> listByPid(Long pid);


    void removeAreaCacheByParentId(Long pid);
}
