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
package org.apache.flink.streaming.connectors.redis.common.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Protocol;

import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;

/**
 * Redis command container if we want to connect to a Redis cluster.
 */
public class RedisClusterContainer implements RedisCommandsContainer, Closeable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(RedisClusterContainer.class);

    private transient JedisCluster jedisCluster;

    /**
     * Initialize Redis command container for Redis cluster.
     *
     * @param jedisCluster JedisCluster instance
     */
    public RedisClusterContainer(JedisCluster jedisCluster) {
        Objects.requireNonNull(jedisCluster, "Jedis cluster can not be null");

        this.jedisCluster = jedisCluster;
    }

    @Override
    public void open() throws Exception {}

    @Override
    public void hset(final String key, final String hashField, final String value, final Integer ttl) {
        try {
            jedisCluster.hset(key, hashField, value);
            if (ttl != null) {
                jedisCluster.expire(key, ttl);
            }
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Cannot send Redis message with command HSET to hash {} of key {} error message {}",
                    hashField, key, e.getMessage());
            }
            throw e;
        }
    }

    @Override
    public void hincrBy(final String key, final String hashField, final Long value, final Integer ttl) {
        try {
            jedisCluster.hincrBy(key, hashField, value);
            if (ttl != null) {
                jedisCluster.expire(key, ttl);
            }
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Cannot send Redis message with command HINCRBY to hash {} of key {} error message {}",
                        hashField, key, e.getMessage());
            }
            throw e;
        }
    }

    @Override
    public void rpush(final String listName, final String value) {
        try {
            jedisCluster.rpush(listName, value);
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Cannot send Redis message with command RPUSH to list {} error message: {}",
                    listName, e.getMessage());
            }
            throw e;
        }
    }

    @Override
    public void lpush(String listName, String value) {
        try {
            jedisCluster.lpush(listName, value);
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Cannot send Redis message with command LPUSH to list {} error message: {}",
                    listName, e.getMessage());
            }
            throw e;
        }
    }

    @Override
    public void sadd(final String setName, final String value) {
        try {
            jedisCluster.sadd(setName, value);
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Cannot send Redis message with command RPUSH to set {} error message {}",
                    setName, e.getMessage());
            }
            throw e;
        }
    }

    @Override
    public void publish(final String channelName, final String message) {
        try {
            jedisCluster.publish(channelName, message);
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Cannot send Redis message with command PUBLISH to channel {} error message {}",
                    channelName, e.getMessage());
            }
            throw e;
        }
    }

    @Override
    public void set(final String key, final String value) {
        try {
            jedisCluster.set(key, value);
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Cannot send Redis message with command SET to key {} error message {}",
                    key, e.getMessage());
            }
            throw e;
        }
    }

    @Override
    public void setex(final String key, final String value, final Integer ttl) {
        try {
            jedisCluster.setex(key, ttl, value);
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Cannot send Redis message with command SETEX to key {} error message {}",
                        key, e.getMessage());
            }
            throw e;
        }
    }

    @Override
    public void pfadd(final String key, final String element) {
        try {
            jedisCluster.pfadd(key, element);
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Cannot send Redis message with command PFADD to key {} error message {}",
                    key, e.getMessage());
            }
            throw e;
        }
    }

    @Override
    public void zadd(final String key, final String score, final String element) {
        try {
            jedisCluster.zadd(key, Double.valueOf(score), element);
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Cannot send Redis message with command ZADD to set {} error message {}",
                    key, e.getMessage());
            }
            throw e;
        }
    }

    @Override
    public void zincrBy(final String key, final String score, final String element) {

        try {
            jedisCluster.zincrby(key, Double.valueOf(score), element);
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Cannot send Redis message with command ZINCRBY to set {} error message {}",
                        key, e.getMessage());
            }
            throw e;
        }
    }

    @Override
    public void zrem(final String key, final String element) {
        try {
            jedisCluster.zrem(key, element);
        } catch (Exception e) {
            if (LOG.isDebugEnabled()) {
                LOG.error("Cannot send Redis message with command ZREM to set {} error message {}",
                    key, e.getMessage());
            }
        }
    }

    @Override
    public void incrByEx(String key, Long value, Integer ttl) {
        try {
            jedisCluster.incrBy(key, value);
            if (ttl != null) {
                jedisCluster.expire(key, ttl);
            }
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Cannot send Redis message with command incrby and ttl to key {} with increment {} and tll {} error message {}",
                        key, value, ttl, e.getMessage());
            }
            throw e;
        }
    }

    @Override
    public void decrByEx(String key, Long value, Integer ttl) {
        try {
            jedisCluster.decrBy(key, value);
            if (ttl != null) {
                jedisCluster.expire(key, ttl);
            }
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Cannot send Redis message with command descry and ttl to key {} with increment {} and tll {} error message {}",
                        key, value, ttl, e.getMessage());
            }
            throw e;
        }
    }


    @Override
    public void incrBy(String key, Long value) {
        try {
            jedisCluster.incrBy(key, value);
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Cannot send Redis message with command incrby to key {} with increment {} error message {}",
                        key, value, e.getMessage());
            }
            throw e;
        }
    }

    @Override
    public void decrBy(String key, Long value) {
        try {
            jedisCluster.decrBy(key, value);
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Cannot send Redis message with command descry to key {} with decrement {} error message {}",
                        key, value, e.getMessage());
            }
            throw e;
        }
    }


    /**
     * Closes the {@link JedisCluster}.
     */
    @Override
    public void close() throws IOException {
        this.jedisCluster.close();
    }

}
