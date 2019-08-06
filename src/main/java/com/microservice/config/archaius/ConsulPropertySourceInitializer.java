package com.microservice.config.archaius;

import com.microservice.config.common.ServcieContants;
import com.netflix.config.*;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author hexudong
 * @description 实现服务启动时读取配置信息
 * @date 2019-08-05
 */
public class ConsulPropertySourceInitializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.setProperty("archaius.fixDelayPollingScheduler.delayMills","10000");

        String keyName = "service/"+ ServcieContants.serviceName+"/"
                + ServcieContants.serviceTag;

        // 创建配置信息读取源实例
        PolledConfigurationSource configSource = new ConsulConfigurationSource(keyName);
        //此类负责调度配置源的定期轮询，并将轮询结果应用于配置。
        AbstractPollingScheduler scheduler = new FixedDelayPollingScheduler();
        //此配置中的属性值将在运行时动态更改，如果配置源中的值更改。
        DynamicConfiguration configuration = new DynamicConfiguration(configSource, scheduler);
        // 将动态配置实例安装到管理器中
        ConfigurationManager.install(configuration);
    }
}
