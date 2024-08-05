package com.leder.super_car.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @className: UserAddrVo
 * @author: xiaomao
 * @date: 2024/7/31 23:19
 * @Version: 1.0
 * @description: 用户地址视图
 */

@Data
public class UserAddrVo {


    // 地址ID，用于唯一标识一个地址
    @JsonSerialize(using = ToStringSerializer.class)
    private Long addrId;

    // 收件人的姓名
    private String receiver;

    // 省份信息
    private String province;

    // 城市信息
    private String city;

    // 区域信息
    private String area;

    // 具体的地址信息
    private String addr;

    // 收件人的联系电话
    private String mobile;

    //是否默认地址（1:是 0：否）
    private Integer commonAddr;

    /**
     * 省ID
     */

    private Long provinceId;

    /**
     * 城市ID
     */

    private Long cityId;

    /**
     * 区域ID
     */

    private Long areaId;

}
