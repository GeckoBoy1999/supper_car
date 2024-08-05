package com.leder.super_car.websocket.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @className: WR
 * @author: xiaomao
 * @date: 2024/7/28 18:11
 * @Version: 1.0
 * @description: socket返回信息
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class WR<T> implements Serializable {

    private String key;

    private T data;


    public WR(T data, String key) {
        this.data = data;
        this.key = key;
    }

    public static <T> WR<T> success(T data, String key) {
        return new WR<>(data, key);

    }
}