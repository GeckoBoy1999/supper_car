package com.leder.super_car.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leder.super_car.mapper.FileDetailPoMapper;
import com.leder.super_car.pojo.po.FileDetail;
import lombok.SneakyThrows;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.recorder.FileRecorder;
import org.dromara.x.file.storage.core.upload.FilePartInfo;
import org.springframework.stereotype.Service;

/**
 * @className: FileRecorderImpl
 * @author: xiaomao
 * @date: 2024/7/29 20:15
 * @Version: 1.0
 * @description: 文件实现类
 */

@Service
public class FileRecorderImpl extends ServiceImpl<FileDetailPoMapper, FileDetail> implements FileRecorder {



    private final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * @Description: 将文件信息转换成数据库存储的文件详情信息
     * @Author: xiaomao
     * @Date: 2024/7/29 23:47
     * @param fileInfo:
     * @return boolean
     */
    @SneakyThrows
    @Override
    public boolean save(FileInfo fileInfo) {
        FileDetail fileDetail = toFileDetail(fileInfo);
        boolean b = save(fileDetail);

        if (b) {
            fileInfo.setId(fileDetail.getId().toString());
        }

        return b;

    }

    @Override
    public void update(FileInfo fileInfo) {

    }

    @Override
    public FileInfo getByUrl(String s) {
        return null;
    }


    /**
     * @Description: 根据文件路径删除文件信息
     * @Author: xiaomao
     * @Date: 2024/7/29 23:47
     * @param url:
     * @return boolean
     */
    @Override
    public boolean delete(String url) {
        remove(Wrappers.lambdaQuery(FileDetail.class).eq(FileDetail::getUrl, url));
        return true;
    }


    @Override
    public void saveFilePart(FilePartInfo filePartInfo) {

    }


    @Override
    public void deleteFilePartByUploadId(String s) {

    }


    /**
     * @Description: 将文件信息转换成数据库存储的文件详情信息
     * @Author: xiaomao
     * @Date: 2024/7/29 20:53
     * @param info:
     * @return com.leder.super_car.pojo.po.SpFileDetailPo
     */
    public FileDetail toFileDetail(FileInfo info) throws JsonProcessingException {
        FileDetail detail = BeanUtil.copyProperties(
                info, FileDetail.class, "metadata", "userMetadata", "thMetadata", "thUserMetadata", "attr", "hashInfo");

        // 这里手动获 元数据 并转成 json 字符串，方便存储在数据库中
        detail.setMetadata(valueToJson(info.getMetadata()));
        detail.setUserMetadata(valueToJson(info.getUserMetadata()));
        detail.setThMetadata(valueToJson(info.getThMetadata()));
        detail.setThUserMetadata(valueToJson(info.getThUserMetadata()));
        // 这里手动获 取附加属性字典 并转成 json 字符串，方便存储在数据库中
        detail.setAttr(valueToJson(info.getAttr()));
        // 这里手动获 哈希信息 并转成 json 字符串，方便存储在数据库中
        detail.setHashInfo(valueToJson(info.getHashInfo()));

        return detail;
    }


    /**
     * 将指定值转换成 json 字符串
     */
    public String valueToJson(Object value) throws JsonProcessingException {
        if (value == null) {
            return null;
        }
        return objectMapper.writeValueAsString(value);
    }

}
