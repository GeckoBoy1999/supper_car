package com.leder.super_car.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @className: Area
 * @author: xiaomao
 * @date: 2024/7/29 20:36
 * @Version: 1.0
 * @description: 省市区实体
 */



@Data
@TableName(value = "sp_area")
@EqualsAndHashCode(callSuper = true)
public class Area extends EntryPo {


    /**
     * id
     */
    @TableId(value = "area_id", type = IdType.AUTO)
    private Long areaId;


    /**
     * 名称
     */
    @TableField(value = "area_name")
    private String areaName;



    /**
     * 上级id
     */
    @TableField(value = "parent_id")
    private Long parentId;



    /**
     * 等级
     */
    @TableField(value = "`level`")
    private Integer level;



    /**
     * 下级列表
     */
    @TableField(exist = false)
    private List<Area> areas;
}