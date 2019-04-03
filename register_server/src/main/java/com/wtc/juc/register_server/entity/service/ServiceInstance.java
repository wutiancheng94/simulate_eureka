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

    /**
     * 续约，发送一次心跳，就相当于把register-client和register-server之间维护的一个契约
     * 进行了续约，我还存活着，我们俩的契约可以维持着
     */
    public void renew() {
        this.lease.renew();
    }

    public boolean isAlive() {
        return this.lease.isAlive();
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

        /**
         * 监控当前服务实例是否存活
         */
        public boolean isAlive() {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - latestHeartbeatTime > NOT_ALIVE_PERIOD) {
                System.out.println("服务实例【" + serviceInstanceId + "】，不再存活");
                return false;
            }

            System.out.println("服务实例【" + serviceInstanceId + "】，保持存活");
            return true;
        }
    }
}
