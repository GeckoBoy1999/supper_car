package com.leder.super_car.websocket.entity;

import lombok.Data;

/**
 * @className: FleeKillExternalRole
 * @author: xiaomao
 * @date: 2024/7/28 18:01
 * @Version: 1.0
 * @description: 对外用户
 */
@Data
public class FleeKillExternalRole {

    /**用户ID**/
    private Long userId;

    /**用户名称**/
    private String username;

    /**房间id**/
    private Integer roomId;

    /**真假机器人**/
    private Boolean isRobot;

    /**皮肤等级免伤(1:普通皮 2:青铜皮肤5% 3:白银皮肤10% 4:黄金皮肤20%)**/
    private Integer skinGrade;
}
