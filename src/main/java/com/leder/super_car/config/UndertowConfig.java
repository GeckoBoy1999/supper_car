package com.leder.super_car.config;

import io.undertow.server.DefaultByteBufferPool;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

/**
 * @className: UndertowConfig
 * @author: xiaomao
 * @date: 2024/7/28 16:39
 * @Version: 1.0
 * @description: Undertow容器配置
 */
@Configuration
public class UndertowConfig implements WebServerFactoryCustomizer<UndertowServletWebServerFactory> {


    /**
     * @Description: 为 WebSocketDeploymentInfo 设置一个合理的参数
     * @Author: xiaomao
     * @Date: 2024/7/28 16:41
     * @param factory:
     */
    @Override
    public void customize(UndertowServletWebServerFactory factory) {

        factory.addDeploymentInfoCustomizers(deploymentInfo -> {

            WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();

            // 设置合理的参数
            webSocketDeploymentInfo.setBuffers(new DefaultByteBufferPool(true, 8192));

            deploymentInfo.addServletContextAttribute("io.undertow.websockets.jsr.WebSocketDeploymentInfo", webSocketDeploymentInfo);
        });

    }
}
