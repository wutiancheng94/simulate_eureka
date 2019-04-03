package com.wtc.juc.register_client.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 〈心跳请求〉
 *
 * @author 吴天成
 * @create 2019/4/3
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeartbeatRequest {

    /**
     * 服务实例id
     */
    private String serviceInstanceId;
}