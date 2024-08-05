package com.leder.super_car.websocket.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @className: FleeKillRole
 * @author: xiaomao
 * @date: 2024/7/28 17:59
 * @Version: 1.0
 * @description: 逃杀角色
 */
@Data
public class FleeKillRole {


    /**用户id**/
    private Long userId;

    /**用户昵称**/
    private String username;

    /**皮肤等级免伤(1:普通皮 2:青铜皮肤5% 3:白银皮肤10% 4:黄金皮肤20%)**/
    private Integer skinGrade;

    /**现有数量**/
    private BigDecimal ownCount;

    /**下注数**/
    private BigDecimal inputCount;

    /**选择房间id**/
    private Integer roomId;
}
