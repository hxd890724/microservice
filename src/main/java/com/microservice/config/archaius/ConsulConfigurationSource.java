package com.microservice.config.archaius;

import com.google.common.base.Optional;
import com.google.common.net.HostAndPort;
import com.netflix.config.PollResult;
import com.netflix.config.PolledConfigurationSource;
import com.orbitz.consul.Consul;
import com.orbitz.consul.KeyValueClient;
import org.apache.commons.lang3.StringUtils;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author hexudong
 * @description 配置信息读取源
 * @date 2019-08-05
 */
public class ConsulConfigurationSource implements PolledConfigurationSource {

    private String keyName;

    public ConsulConfigurationSource(String keyName) {
        this.keyName = keyName;
    }

    @Override
    public PollResult poll(boolean b, Object o) throws Exception {
        Consul consul = Consul.builder().withHostAndPort(HostAndPort.
                fromString("172.20.10.14:8500")).build();
        KeyValueClient kvClient = consul.keyValueClient();
        Optional<String> kvOpt = kvClient.getValueAsString(keyName);
        String kvStr = StringUtils.EMPTY;
        if (kvOpt.isPresent()){
            kvStr = kvOpt.get();
        }
        Properties properties = new Properties();
        properties.load(new StringReader(kvStr));
        Map<String, Object> propMap = new HashMap<>();
        for (Object key: properties.keySet()) {
            propMap.put((String) key, properties.get(key));
        }
        return PollResult.createFull(propMap);
    }
}
