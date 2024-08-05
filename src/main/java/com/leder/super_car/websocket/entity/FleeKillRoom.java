package com.leder.super_car.websocket.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @className: FleeKillRoom
 * @author: xiaomao
 * @date: 2024/7/28 18:03
 * @Version: 1.0
 * @description: 房间信息
 */
@Data
public class FleeKillRoom {


    /**房间id**/
    private Long roomId;

    /**房间当前下注总数**/
    private BigDecimal roomInputSum;

}
