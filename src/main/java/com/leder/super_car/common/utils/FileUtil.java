package com.leder.super_car.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.feiniaojin.gracefulresponse.GracefulResponse;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @className: FileUtil
 * @author: xiaomao
 * @date: 2024/7/27 18:15
 * @Version: 1.0
 * @description: 文件工具类
 */
@Component
public class FileUtil {


    @Resource
    private FileStorageService fileStorageService;


    /**
     * @Description: 上传文件
     * @Author: xiaomao
     * @Date: 2024/7/27 21:01
     * @param file:  图片
     * @return org.dromara.x.file.storage.core.FileInfo
     */       
    public FileInfo upload(MultipartFile file) {
        FileInfo upload = fileStorageService.of(file).setPath(DateUtil.format(new Date(), "yyyy-MM-dd") + "/").upload();
        GracefulResponse.wrapAssert("511",()->Assert.notNull(file,"文件上传异常"));
        return upload;


    }

    /**
     * @Description: 删除文件
     * @Author: xiaomao
     * @Date: 2024/7/31 21:21
     * @param url:
     * @return java.lang.Boolean
     */
    public Boolean delFile(String url){
       return  fileStorageService.delete(url);
    }

}

