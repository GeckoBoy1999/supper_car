package com.leder.super_car.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @className: FileDetailPo
 * @author: xiaomao
 * @date: 2024/7/29 20:36
 * @Version: 1.0
 * @description: 文件信息实体类
 */

@Data
@TableName("sp_file_detail")
@EqualsAndHashCode(callSuper = true)
public class FileDetail extends EntryPo{


    /**
     * 主键id
     */
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;



    /**
     * 文件访问地址
     */
    private String url;

    /**
     * 文件大小，单位字节
     */
    private Long size;

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 原始文件名
     */
    @TableField(value = "original_filename")
    private String originalFilename;

    /**
     * 基础存储路径
     */
    @TableField(value = "base_path")
    private String basePath;

    /**
     * 存储路径
     */
    private String path;

    /**
     * 文件扩展名
     */
    private String ext;

    /**
     * MIME类型
     */
    @TableField(value = "content_type")
    private String contentType;

    /**
     * 存储平台
     */
    private String platform;

    /**
     * 缩略图访问路径
     */
    @TableField(value = "th_url")
    private String thumbnailUrl;

    /**
     * 缩略图名称
     */
    @TableField(value = "th_filename")
    private String thumbnailFilename;

    /**
     * 缩略图大小，单位字节
     */
    @TableField(value = "th_size")
    private Long thumbnailSize;

    /**
     * 缩略图MIME类型
     */
    @TableField(value = "th_content_type")
    private String thumbnailContentType;

    /**
     * 文件所属对象id
     */
    @TableField(value = "object_id")
    private String objectId;

    /**
     * 文件所属对象类型，例如用户头像，评价图片
     */
    @TableField(value = "object_type")
    private String objectType;

    /**
     * 文件元数据
     */
    private String metadata;

    /**
     * 文件用户元数据
     */
    @TableField(value = "user_metadata")
    private String userMetadata;

    /**
     * 缩略图元数据
     */
    @TableField(value = "th_metadata")
    private String thMetadata;

    /**
     * 缩略图用户元数据
     */
    @TableField(value = "th_user_metadata")
    private String thUserMetadata;

    /**
     * 附加属性
     */
    private String attr;

    /**
     * 文件ACL
     */
    @TableField(value = "file_acl")
    private String fileAcl;

    /**
     * 缩略图文件ACL
     */
    @TableField(value = "th_file_acl")
    private String thumbnailFileAcl;

    /**
     * 哈希信息
     */
    @TableField(value = "hash_info")
    private String hashInfo;

    /**
     * 上传ID，仅在手动分片上传时使用
     */
    @TableField(value = "upload_id")
    private String uploadId;

    /**
     * 上传状态，仅在手动分片上传时使用，1：初始化完成，2：上传完成
     */
    @TableField(value = "upload_status")
    private Integer uploadStatus;
}
