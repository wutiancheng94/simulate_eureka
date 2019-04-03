package com.wtc.juc.register_client.worker;

import com.wtc.juc.register_client.request.HeartbeatRequest;
import com.wtc.juc.register_client.request.RegisterRequest;
import com.wtc.juc.register_client.response.HeartbeatResponse;
import com.wtc.juc.register_client.response.RegisterResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 〈负责发送各种Http请求的组件〉
 *
 * @author 吴天成
 * @create 2019/4/3
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
public class HttpSender {

    private RegisterRequest registerRequest;

    /**
     * 发送注册请求
     * @param registerRequest
     * @return
     */
    public RegisterResponse register(RegisterRequest registerRequest) {
        System.out.println("服务实例【" + registerRequest.getServiceInstanceId() + "】，发送请求进行注册......");
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setStatus(RegisterResponse.SUCCESS);
        return registerResponse;
    }

    /**
     * 发送心跳请求
     * @param heartbeatRequest
     * @return
     */
    public HeartbeatResponse heartbeat(HeartbeatRequest heartbeatRequest) {
        System.out.println("服务实例【" + heartbeatRequest.getServiceInstanceId() + "】，发送请求进行心跳......");
        HeartbeatResponse heartbeatResponse = new HeartbeatResponse();
        heartbeatResponse.setStatus(HeartbeatResponse.SUCCESS);
        return heartbeatResponse;
    }

}