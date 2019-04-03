package com.wtc.juc.register_server.entity.register;

import com.wtc.juc.register_server.entity.service.ServiceInstance;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈注册表〉
 *
 * @author 吴天成
 * @create 2019/4/3
 * @since 1.0.0
 */
public class Registry {

    /**
     * <服务名称，<服务实例id, 服务实例信息>>
     */
    private Map<String, Map<String, ServiceInstance>> registry = new HashMap<>();

    /**
     * 注册表是单例
     */
    private static Registry instance = new Registry();

    private Registry() {
    }

    public static Registry getInstance() {
        return instance;
    }

    /**
     * 服务注册
     * @param serviceInstance 服务实例
     */
    public void register(ServiceInstance serviceInstance) {
        Map<String, ServiceInstance> serviceInstanceMap = registry.get(serviceInstance.getServiceName());
        if (serviceInstanceMap == null) {
            serviceInstanceMap = new HashMap<>();
            serviceInstanceMap.put(serviceInstance.getServiceInstanceId(), serviceInstance);
            registry.put(serviceInstance.getServiceName(), serviceInstanceMap);
        }

        serviceInstanceMap.put(serviceInstance.getServiceInstanceId(), serviceInstance);

        System.out.println("服务实例【" + serviceInstance + "】，完成注册......");
        System.out.println("注册表：" + registry);
    }

    /**
     * 获取服务
     * @param serviceName        服务名称
     * @param serviceInstanceId  服务实例id
     * @return
     */
    public ServiceInstance get(String serviceName, String serviceInstanceId) {
        if (StringUtils.isNoneBlank(serviceName)) {
            Map<String, ServiceInstance> serviceInstanceMap = registry.get(serviceName);
            if (serviceInstanceMap != null) {
                return serviceInstanceMap.get(serviceInstanceId);
            }
        }

        return null;
    }

    /**
     * 获取整个注册表
     * @return
     */
    public Map<String, Map<String, ServiceInstance>> getRegistry() {
        return registry;
    }

    /**
     * 从注册表删除一个服务实例
     * @param serviceName
     * @param serviceInstanceId
     */
    public void remove(String serviceName, String serviceInstanceId) {
        if (StringUtils.isNoneBlank(serviceName)) {
            Map<String, ServiceInstance> serviceInstanceMap = registry.get(serviceName);
            if (serviceInstanceMap != null) {
                serviceInstanceMap.remove(serviceInstanceId);
                System.out.println("服务实例【" + serviceInstanceId + "】，从注册表中进行摘除");
            }
        }
    }
}