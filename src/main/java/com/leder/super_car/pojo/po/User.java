package com.leder.super_car.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @className: User
 * @author: xiaomao
 * @date: 2024/7/29 20:36
 * @Version: 1.0
 * @description: 用户实体类
 */
@Data
@TableName(value = "sp_car_user")
@EqualsAndHashCode(callSuper = true)
public class User extends EntryPo {


    /**
     * 主键id
     */

    @TableId(value = "user_id",type = IdType.ASSIGN_ID)
    private Long userId;


    /**
     * 手机号
     */
    @TableField(value = "mobile")
    private String mobile;

    /**
     * 用户昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 用户编号
     */
    @TableField(value = "user_code")
    private String userCode;

    /**
     * 用户头像
     */
    @TableField(value = "head_image")
    private String headImage;

    /**
     * 密码
     */
    @TableField(value = "`password`")
    private String password;

    /**
     * 交易密码
     */
    @TableField(value = "pay_password")
    private String payPassword;

    /**
     * 性别 0未知  1男   2女
     */
    @TableField(value = "user_sex")
    private Byte userSex;

    /**
     * 实名认证 ID
     */
    @TableField(value = "cert_id")
    private Long certId;

    /**
     * 会员级别 ID
     */
    @TableField(value = "level_id")
    private Integer levelId;

    /**
     * 余额
     */
    @TableField(value = "balance")
    private BigDecimal balance;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 状态 1正常 2禁止登陆
     */
    @TableField(value = "`status`")
    private Byte status;

    /**
     * 代币
     */
    @TableField(value = "food")
    private BigDecimal food;

    /**
     *  0 未赠送初始矿场          1已赠送初始矿场
     */
    @TableField(value = "is_mine")
    private Boolean isMine;

    /**
     * 微信
     */
    @TableField(value = "wechat")
    private String wechat;

    /**
     * qq
     */
    @TableField(value = "qq")
    private String qq;

    /**
     * 注册ip
     */
    @TableField(value = "register_ip")
    private String registerIp;

    /**
     * 注册时间
     */
    @TableField(value = "register_time")
    private Date registerTime;

    /**
     * 注册来源
     */
    @TableField(value = "source")
    private String source;

}