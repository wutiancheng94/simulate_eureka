package com.wtc.juc.register_client.main;

import com.wtc.juc.register_client.worker.RegisterClientWorker;

import java.util.UUID;

/**
 * 〈在服务上被创建和启动，负责跟register-server进行通信〉
 *
 * @author 吴天成
 * @create 2019/4/3
 * @since 1.0.0
 */
public class RegisterClient {

    /**
     * 服务实例id
     */
    private String serviceInstanceId;

    public void setServiceInstanceId() {
        this.serviceInstanceId = UUID.randomUUID().toString();
    }

    public void start() {
        new RegisterClientWorker(serviceInstanceId).start();
    }
}