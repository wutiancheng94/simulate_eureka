package com.wtc.juc.register_server.controller;

import com.wtc.juc.register_server.entity.register.RegisterRequest;
import com.wtc.juc.register_server.entity.register.RegisterResponse;
import com.wtc.juc.register_server.entity.register.Registry;
import com.wtc.juc.register_server.entity.service.Lease;
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
            serviceInstance.setLease(new Lease());

            registry.register(serviceInstance);

            registerResponse.setStatus(RegisterResponse.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            registerResponse.setStatus(RegisterResponse.FAILURE);
        }

        return registerResponse;
    }
}