package com.wtc.juc.register_client.response;

import lombok.Data;

/**
 * 〈心跳响应〉
 *
 * @author 吴天成
 * @create 2019/4/3
 * @since 1.0.0
 */
@Data
public class HeartbeatResponse {
    /**
     * 响应成功
     */
    public static final String SUCCESS = "success";

    /**
     * 响应失败
     */
    public static final String FAILURE = "failure";

    /**
     * 心跳响应状态：SUCCESS、FAILURE
     */
    private String status;
}