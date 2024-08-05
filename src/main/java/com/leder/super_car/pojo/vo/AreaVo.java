package com.leder.super_car.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.leder.super_car.pojo.po.Area;
import lombok.Data;

import java.util.List;

/**
 * @className: AreaVo
 * @author: xiaomao
 * @date: 2024/7/31 22:54
 * @Version: 1.0
 * @description: 省市区试图
 */

@Data
public class AreaVo {


    /**
     * id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long areaId;


    /**
     * 名称
     */

    private String areaName;



    /**
     * 上级id
     */

    private Long parentId;



    /**
     * 等级
     */

    private Integer level;



    /**
     * 下级列表
     */

    private List<Area> areas;

}
