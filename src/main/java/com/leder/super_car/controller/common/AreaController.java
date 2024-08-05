package com.leder.super_car.controller.common;

import com.leder.super_car.pojo.vo.AreaVo;
import com.leder.super_car.service.AreaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @className: AreaController
 * @author: xiaomao
 * @date: 2024/7/31 22:38
 * @Version: 1.0
 * @description: 省市区接口
 */

@RestController
@RequestMapping("/area")
public class AreaController {




    @Resource
    private AreaService areaService;


    @Value("${api.version}")
    private String apiVersion;



    /**
     * @Description: 根据省市区的pid获取地址信息 ,省市区的pid(pid为0获取所有省份)
     * @Author: xiaomao
     * @Date: 2024/7/31 22:39
     * @param pid:
     * @return ServerResponseEntity<List < Area>>
     */
    @GetMapping("/${api.version}/listByPid")
    public List<AreaVo> listByPid(Long pid){

        return  areaService.listByPid(pid);

    }
}
