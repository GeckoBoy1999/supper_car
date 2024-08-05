package com.leder.super_car.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @className: EntryPo
 * @author: xiaomao
 * @date: 2024/7/27 17:27
 * @Version: 1.0
 * @description: 实体类基类$
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntryPo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time",select = false)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time",select = false)
    private LocalDateTime updateTime;


    /**
     * 是否删除
     */
    @TableLogic
    @TableField(value = "is_deleted",select = false)
    private Integer isDeleted;

}
