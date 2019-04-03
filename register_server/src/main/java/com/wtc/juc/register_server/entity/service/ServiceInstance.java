package com.wtc.juc.register_server.entity.service;

import lombok.Data;

/**
 * 〈服务实例〉
 *
 * @author 吴天成
 * @create 2019/4/3
 * @since 1.0.0
 */
@Data
public class ServiceInstance {

    /**
     * 判断一个服务实例不再存活的周期
     */
    private static final Long NOT_ALIVE_PERIOD = 90 * 1000L;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 主机名
     */
    private String hostname;

    /**
     * 端口号
     */
    private int port;

    /**
     * 服务实例id
     */
    private String serviceInstanceId;

    /**
     * 契约
     */
    private Lease lease;

    public ServiceInstance() {
        this.lease = new Lease();
    }

    public void renew() {
        this.lease.renew();
    }

    private class Lease {

        /**
         * 最近心跳时间
         */
        private long latestHeartbeatTime;

        public void renew() {
            this.latestHeartbeatTime = System.currentTimeMillis();
            System.out.println("服务实例【" + serviceInstanceId + "】，进行续约：" + latestHeartbeatTime);
        }
    }
}
