package com.wtc.juc.register_server;

import com.wtc.juc.register_server.controller.RegisterServerController;
import com.wtc.juc.register_server.entity.register.RegisterRequest;

import java.util.UUID;

public class RegisterServerApplication {

    public static void main(String[] args) {
        RegisterServerController serverController = new RegisterServerController();

        // 模拟发起一个服务注册的请求
        String serviceInstanceId = UUID.randomUUID().toString().replace("-", "");
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setHostname("inventory-service-01");
        registerRequest.setIp("192.168.31.208");
        registerRequest.setPort(9000);
        registerRequest.setServiceInstanceId(serviceInstanceId);
        registerRequest.setServiceName("inventory-service");
        serverController.register(registerRequest);

        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
