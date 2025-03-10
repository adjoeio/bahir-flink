/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.flink.streaming.connectors.redis.common.config;

import org.apache.flink.util.TestLogger;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class JedisClusterConfigTest extends TestLogger {

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointExceptionIfNodeValueIsNull(){
        FlinkJedisClusterConfig.Builder builder = new FlinkJedisClusterConfig.Builder();
        builder.setMinIdle(0)
            .setMaxIdle(0)
            .setMaxTotal(0)
            .setTimeout(0)
            .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfNodeValuesAreEmpty(){
        Set<InetSocketAddress> set = new HashSet<>();
        FlinkJedisClusterConfig.Builder builder = new FlinkJedisClusterConfig.Builder();
        builder.setMinIdle(0)
            .setMaxIdle(0)
            .setMaxTotal(0)
            .setTimeout(0)
            .setNodes(set)
            .build();
    }

    @Test
    public void shouldSetPasswordSuccessfully() {
        Set<InetSocketAddress> set = new HashSet<>();
        InetSocketAddress address = InetSocketAddress.createUnresolved("localhost", 8080);
        set.add(address);
        FlinkJedisClusterConfig.Builder builder = new FlinkJedisClusterConfig.Builder();
        FlinkJedisClusterConfig clusterConfig = builder.setMinIdle(0)
                .setMaxIdle(0)
                .setMaxTotal(0)
                .setTimeout(0)
                .setNodes(set)
                .setPassword("test-pwd")
                .build();
        assertEquals("test-pwd", clusterConfig.getPassword());
    }

    @Test
    public void shouldPasswordNotBeenSet() {
        Set<InetSocketAddress> set = new HashSet<>();
        InetSocketAddress address = InetSocketAddress.createUnresolved("localhost", 8080);
        set.add(address);
        FlinkJedisClusterConfig.Builder builder = new FlinkJedisClusterConfig.Builder();
        FlinkJedisClusterConfig clusterConfig = builder.setMinIdle(0)
                .setMaxIdle(0)
                .setMaxTotal(0)
                .setTimeout(0)
                .setNodes(set)
                .build();
        assertNull(clusterConfig.getPassword());
    }

    @Test
    public void shouldSetSslSuccessfully() {
        Set<InetSocketAddress> set = new HashSet<>();
        InetSocketAddress address = InetSocketAddress.createUnresolved("localhost", 8080);
        set.add(address);
        FlinkJedisClusterConfig.Builder builder = new FlinkJedisClusterConfig.Builder();
        FlinkJedisClusterConfig clusterConfig = builder.setMinIdle(0)
                .setMaxIdle(0)
                .setMaxTotal(0)
                .setTimeout(0)
                .setNodes(set)
                .setSsl(true)
                .build();
        assertTrue(clusterConfig.getSsl());
    }

    @Test
    public void shouldSslNotBeenSet() {
        Set<InetSocketAddress> set = new HashSet<>();
        InetSocketAddress address = InetSocketAddress.createUnresolved("localhost", 8080);
        set.add(address);
        FlinkJedisClusterConfig.Builder builder = new FlinkJedisClusterConfig.Builder();
        FlinkJedisClusterConfig clusterConfig = builder.setMinIdle(0)
                .setMaxIdle(0)
                .setMaxTotal(0)
                .setTimeout(0)
                .setNodes(set)
                .build();
        assertFalse(clusterConfig.getSsl());
    }

}
