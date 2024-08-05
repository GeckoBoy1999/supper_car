package com.leder.super_car.controller.common;

import com.feiniaojin.gracefulresponse.api.ValidationStatusCode;
import com.leder.super_car.common.annotate.NoAuthorization;
import com.leder.super_car.common.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.recorder.FileRecorder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @className: FileController
 * @author: xiaomao
 * @date: 2024/7/29 23:48
 * @Version: 1.0
 * @description: 文件控制层
 */

@Slf4j
@Validated
@RestController
@RequestMapping("/file")
public class FileController {


    @Value("${api.version}")
    private String apiVersion;


    @Resource
    private FileUtil fileUtil;


    @Resource
    private FileRecorder fileRecorder;



    /**
     * @Description: 文件上传
     * @Author: xiaomao
     * @Date: 2024/7/27 21:14
     * @param file: 文件
     * @return org.dromara.x.file.storage.core.FileInfo
     */
    @NoAuthorization
    @PostMapping("/${api.version}/upload")
    @ValidationStatusCode(code = "510")
    public FileInfo upload(@NotNull(message = "文件不能为空") @RequestPart("file") MultipartFile file){
        return fileUtil.upload(file);

    }


    /**
     * @Description: 删除文件
     * @Author: xiaomao
     * @Date: 2024/7/29 23:51
     * @param url:
     * @return java.lang.Boolean
     */
    @NoAuthorization
    @DeleteMapping("/${api.version}/deleted")
    @ValidationStatusCode(code = "510")
    public Boolean deletedFile(@NotEmpty(message = "文件地址不能为空") @RequestParam("url") String url){
        Boolean minioDel = fileUtil.delFile(url);
        Boolean sqlDel = fileRecorder.delete(url);
        return minioDel && sqlDel;


    }



}
