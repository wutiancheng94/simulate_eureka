package com.wtc.juc.register_client.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 〈注册请求〉
 *
 * @author 吴天成
 * @create 2019/4/3
 * @since 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 服务所在机器的ip地址
     */
    private String ip;
    /**
     * 服务所在机器的主机名
     */
    private String hostname;
    /**
     * 服务监听着哪个端口号
     */
    private int port;
    /**
     * 服务实例
     */
    private String serviceInstanceId;
}