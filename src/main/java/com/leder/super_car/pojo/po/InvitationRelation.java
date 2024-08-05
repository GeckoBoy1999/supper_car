package com.leder.super_car.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @className: InvitationRelation
 * @author: xiaomao
 * @date: 2024/8/3 23:28
 * @Version: 1.0
 * @description: 用户邀请关系
 */

@Data
@Builder
@TableName(value = "sp_invitation_relations")
@AllArgsConstructor
@NoArgsConstructor
public class InvitationRelation {


    /**
     * 主键，用于唯一标识每条邀请记录。
     */
    @TableId(value = "id",type = IdType.AUTO)
    private int id;

    /**
     * 被邀请用户的用户ID，对应于 users 表中的用户。
     */
    private Long invitedUserId;

    /**
     * 直接邀请者的用户ID，对应于 users 表中的用户。
     */
    private Long inviterUserId;

    /**
     * 间接邀请者的用户ID，即邀请者的上级邀请者。
     * 如果是一级邀请，则此字段为 null。
     */
    private Long secondaryInviterUserId;

    /**
     * 邀请层级，1 表示一级邀请，2 表示二级邀请。
     */
    private Integer level;

    /**
     * 创建时间，记录邀请关系创建的时间。
     */
    @TableField(value = "create_time",select = false)
    private LocalDateTime createTime;
}
