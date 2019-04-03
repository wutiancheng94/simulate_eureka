package com.wtc.juc.register_server.entity.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 契约
 * 维护了一个服务实例跟当前的这个注册中心之间的联系
 * 包括了心跳的时间，创建的时间，等等
 *
 * @author 吴天成
 * @create 2019/4/3
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lease {

    /**
     * 最近心跳时间
     */
    private long lastHeartbeatTime = System.currentTimeMillis();
}