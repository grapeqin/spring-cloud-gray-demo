package com.example.demo.gray.rule;

import com.example.demo.gray.util.HystrixRequestVariableDefaultUtils;
import com.google.common.base.Optional;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 重写Ribbon的负载均衡策略
 */
public class GrayMetadataRule extends ZoneAvoidanceRule {

    private static final Logger logger = LoggerFactory.getLogger(GrayMetadataRule.class);

    @Override
    public Server choose(Object key) {
        String hystrixVersion = HystrixRequestVariableDefaultUtils.version.get();
        logger.debug("======>GrayMetadataRule:  hystrixVersion{}", hystrixVersion);

        List<Server> serverList = this.getPredicate().getEligibleServers(this.getLoadBalancer().getAllServers(), key);
        if (CollectionUtils.isEmpty(serverList)) {
            return null;
        }

        List<Server> noMetaServerList = new ArrayList<>();
        for (Server server : serverList) {
            Map<String, String> metadata = ((DiscoveryEnabledServer) server).getInstanceInfo().getMetadata();
            String metaVersion = metadata.get(HystrixRequestVariableDefaultUtils.HEADER_VERSION);
            if (!StringUtils.isEmpty(metaVersion)) {
                if (metaVersion.equals(hystrixVersion)) {
                    return server;
                }
            } else {
                noMetaServerList.add(server);
            }
        }

        if (!noMetaServerList.isEmpty()) {
            return originChoose(noMetaServerList, key);
        }
        return null;
    }

    /**
     * 未配置metadata的provider继续走轮询策略
     *
     * @param noMetaServerList
     * @param key
     * @return
     */
    private Server originChoose(List<Server> noMetaServerList, Object key) {
        Optional<Server> server = getPredicate().chooseRoundRobinAfterFiltering(noMetaServerList, key);
        if (server.isPresent()) {
            return server.get();
        } else {
            return null;
        }
    }
}
