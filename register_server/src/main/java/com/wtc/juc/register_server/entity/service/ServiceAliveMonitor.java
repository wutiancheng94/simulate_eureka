package com.wtc.juc.register_server.entity.service;

import com.wtc.juc.register_server.entity.register.Registry;

import java.util.Map;

/**
 * 〈微服务存活状态监控组件〉
 *
 * @author 吴天成
 * @create 2019/4/4
 * @since 1.0.0
 */
public class ServiceAliveMonitor {

    /**
     * 检查服务实例是否存活的间隔
     */
    private static final Long CHECK_ALIVE_INTERVAL = 60 * 1000L;

    private CheckedLife checkedLife;

    public ServiceAliveMonitor() {
        this.checkedLife = new CheckedLife();
    }

    public void start() {
        this.checkedLife.start();
    }

    private class CheckedLife extends Thread {
        private Registry registry = Registry.getInstance();

        @Override
        public void run() {
            Map<String, Map<String, ServiceInstance>> services = null;
            try {
                services = this.registry.getRegistry();
                for (String serviceName : services.keySet()) {
                    Map<String, ServiceInstance> serviceInstanceMap = services.get(serviceName);
                    for (String serviceInstance : serviceInstanceMap.keySet()) {
                        ServiceInstance instance = serviceInstanceMap.get(serviceInstance);
                        if (!instance.isAlive()) {
                            System.out.println("服务实例【"  + instance+ "】，已被移除......");
                            registry.remove(serviceName, instance.getServiceInstanceId());
                        }
                    }
                }

                Thread.sleep(CHECK_ALIVE_INTERVAL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}