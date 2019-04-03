package com.wtc.juc.register_client.main;

import com.wtc.juc.register_client.request.HeartbeatRequest;
import com.wtc.juc.register_client.request.RegisterRequest;
import com.wtc.juc.register_client.response.HeartbeatResponse;
import com.wtc.juc.register_client.response.RegisterResponse;
import com.wtc.juc.register_client.worker.HttpSender;

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
     *  http通信组件
     */
    private HttpSender httpSender;

    /**
     * 服务实例id
     */
    private String serviceInstanceId;

    /**
     * 服务名
     */
    public static final String SERVICE_NAME = "inventory-service";

    /**
     * 服务ip
     */
    public static final String IP = "192.168.31.207";

    /**
     * 主机名
     */
    public static final String HOSTNAME = "inventory01";

    /**
     * 端口
     */
    public static final int PORT = 9000;

    /**
     * 心跳间隔
     */
    private long heartBeatInterval = 30 * 1000L;

    public RegisterClient() {
        this.httpSender = new HttpSender();
        this.serviceInstanceId = UUID.randomUUID().toString().replace("-", "");
    }

    public void start() {
        RegisterClientWorker registerClientWorker = new RegisterClientWorker();
        HeartbeatWorker heartbeatWorker = new HeartbeatWorker();
        try {
            registerClientWorker.start();
            registerClientWorker.join();
            heartbeatWorker.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 服务注册线程
     */
    private class RegisterClientWorker extends Thread {
        @Override
        public void run() {
            RegisterRequest registerRequest
                    = RegisterRequest.builder()
                    .serviceName(SERVICE_NAME)
                    .port(PORT)
                    .ip(IP)
                    .hostname(HOSTNAME)
                    .serviceInstanceId(serviceInstanceId).build();
            RegisterResponse registerResponse = httpSender.register(registerRequest);
            System.out.println("服务注册的结果是：" + registerResponse.getStatus() + "......");
        }
    }

    /**
     * 心跳检测线程
     */
    private class HeartbeatWorker extends Thread {
        @Override
        public void run() {
            //  注册成功发送心跳
            HeartbeatRequest heartbeatRequest = new HeartbeatRequest();

            heartbeatRequest.setServiceInstanceId(serviceInstanceId);
            heartbeatRequest.setServiceName(SERVICE_NAME);

            while (true) {
                HeartbeatResponse heartbeatResponse = httpSender.heartbeat(heartbeatRequest);
                System.out.println("服务注册的结果是：" + heartbeatResponse.getStatus() + "......");
                try {
                    Thread.sleep(heartBeatInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}