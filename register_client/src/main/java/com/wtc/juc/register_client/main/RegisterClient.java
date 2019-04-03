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
     * 服务实例id
     */
    private String serviceInstanceId;

    public RegisterClient() {
        this.serviceInstanceId = UUID.randomUUID().toString().replace("-", "");
    }

    public void start() {
        new RegisterClientWorker(serviceInstanceId).start();
    }

    /**
     * 负责向register-server发起注册申请的线程
     */
    private class RegisterClientWorker extends Thread {

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
         *  http通信组件
         */
        private HttpSender httpSender;

        /**
         * 服务实例id
         */
        private String serviceInstanceId;

        /**
         * 是否完成服务注册
         */
        private boolean finishedRegister;

        /**
         * 心跳间隔
         */
        private long heartBeatInterval = 30 * 1000L;

        public RegisterClientWorker(String serviceInstanceId) {
            this.httpSender = new HttpSender();
            this.serviceInstanceId = serviceInstanceId;
        }

        @Override
        public void run() {
            RegisterRequest registerRequest
                    = RegisterRequest.builder()
                    .serviceName(SERVICE_NAME)
                    .port(PORT)
                    .ip(IP)
                    .hostname(HOSTNAME)
                    .serviceInstanceId(serviceInstanceId).build();

            //  开始注册
            if (!finishedRegister) {
                RegisterResponse registerResponse = httpSender.register(registerRequest);
                System.out.println("服务注册的结果是：" + registerResponse.getStatus() + "......");

                if (registerResponse.getStatus() != RegisterResponse.SUCCESS) {
                    return;
                }

                finishedRegister = true;
            }

            //  注册成功发送心跳
            if (finishedRegister) {
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
}