package com.wtc.juc.register_server.controller;

import com.wtc.juc.register_server.entity.heartbeat.HeartbeatRequest;
import com.wtc.juc.register_server.entity.heartbeat.HeartbeatResponse;
import com.wtc.juc.register_server.entity.register.RegisterRequest;
import com.wtc.juc.register_server.entity.register.RegisterResponse;
import com.wtc.juc.register_server.entity.register.Registry;
import com.wtc.juc.register_server.entity.service.ServiceInstance;


/**
 * 〈接受register-client发送的注册请求〉
 *
 * @author 吴天成
 * @create 2019/4/3
 * @since 1.0.0
 */
public class RegisterServerController {

    private Registry registry = Registry.getInstance();

    /**
     * 服务注册
     * @param registerRequest   注册请求
     * @return  注册响应
     */
    public RegisterResponse register(RegisterRequest registerRequest) {
        RegisterResponse registerResponse = new RegisterResponse();

        try {
            ServiceInstance serviceInstance = new ServiceInstance();
            serviceInstance.setServiceName(registerRequest.getServiceName());
            serviceInstance.setIp(registerRequest.getIp());
            serviceInstance.setHostname(registerRequest.getHostname());
            serviceInstance.setPort(registerRequest.getPort());
            serviceInstance.setServiceInstanceId(registerRequest.getServiceInstanceId());

            registry.register(serviceInstance);

            registerResponse.setStatus(RegisterResponse.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            registerResponse.setStatus(RegisterResponse.FAILURE);
        }

        return registerResponse;
    }

    /**
     * 心跳续约
     * @param heartbeatRequest 心跳请求
     * @return  心跳响应
     */
    public HeartbeatResponse heartbeat(HeartbeatRequest heartbeatRequest) {
        HeartbeatResponse heartbeatResponse = null;
        try {
            heartbeatResponse = new HeartbeatResponse();
            ServiceInstance serviceInstance = registry.get(heartbeatRequest.getServiceName(), heartbeatRequest.getServiceInstanceId());
            serviceInstance.renew();
            heartbeatResponse.setStatus(HeartbeatResponse.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            heartbeatResponse.setStatus(HeartbeatResponse.FAILURE);
        }
        return heartbeatResponse;
    }
}