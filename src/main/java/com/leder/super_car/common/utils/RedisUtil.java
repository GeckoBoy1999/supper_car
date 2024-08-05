package com.leder.super_car.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Range;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @className: RedisUtil
 * @author: xiaomao
 * @date: 2024/7/28 17:52
 * @Version: 1.0
 * @description: redis工具类
 */
@Slf4j
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    // =============================common============================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     */
    public void expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            log.error("exception message", e);
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public Long getExpire(String key){
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }



    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在   false不存在
     */
    public Boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("exception message", e);
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }
// ============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }



    /**
     * @Description: 批量获取指定前缀的键
     * @Author: TOM
     * @Date: 2023/12/28 17:59
     * @param prefix 前缀
     * @return java.util.List<java.lang.Object>
     */
    public Set<String> batchGet(String prefix){
        Set<String> keys = redisTemplate.keys(prefix + "*");
        assert keys != null;
        return keys;

    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            log.error("exception message", e);
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(毫秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time,
                        TimeUnit.MILLISECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        Long increment = redisTemplate.opsForValue().increment(key, delta);
        assert increment != null;
        return increment;

    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        Long increment = redisTemplate.opsForValue().increment(key, -delta);
        assert increment != null;
        return increment;
    }
// ================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     */
    public Object hGet(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmGet(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     */
    public boolean hmSet(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmSet(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hSet(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hSet(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hDel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }



    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     */
    public Long hIncr(String key, String item, long by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }



    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     */
    public double hDecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }
// ============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("exception message", e);
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        try {
            Boolean member = redisTemplate.opsForSet().isMember(key, value);
            assert member != null;
            return member;
        } catch (Exception e) {
            log.error("exception message", e);
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object... values) {
        try {
            Long add = redisTemplate.opsForSet().add(key, values);
            assert add != null;
            return add;
        } catch (Exception e) {
            log.error("exception message", e);
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            assert count != null;
            return count;
        } catch (Exception e) {
            log.error("exception message", e);
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     */
    public long sGetSetSize(String key) {
        try {
            Long size = redisTemplate.opsForSet().size(key);
            assert size != null;
            return size;
        } catch (Exception e) {
            log.error("exception message", e);
            return 0;
        }
    }


    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            assert count != null;
            return count;
        } catch (Exception e) {
            log.error("exception message", e);
            return 0;
        }
    }
// ===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("exception message", e);
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     */
    public long lGetListSize(String key) {
        try {
            Long size = redisTemplate.opsForList().size(key);
            assert size != null;
            return size;
        } catch (Exception e) {
            log.error("exception message", e);
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0
     *              时，-1，表尾，-2倒数第二个元素，依次类推
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error("exception message", e);
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return boolean
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return boolean
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
            return false;
        }
    }


    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return boolean
     */
    public boolean lUpdateIndex (String key,long index, Object value){
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
            return false;
        }
    }


    /**
     * 移除N个值为value
     *
     * @param key 键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove (String key,long count, Object value){
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            assert remove != null;
            return remove;
        } catch (Exception e) {
            log.error("exception message", e);
            return 0;
        }
    }

    /**
     * @Description:  添加hyperloglog
     * @Author: lqy
     * @param key 键
     * @param value 值
     * @return: long
     */
    public long addHyLog(String key,Object value){
        try {
            return redisTemplate.opsForHyperLogLog().add(key, value);
        } catch (Exception e) {
            log.error("exception message", e);
            return 0;
        }
    }

    /**
     * @Description:  查询hyperloglog
     * @Author: lqy
     * @param key 键
     * @return: long
     */
    public long getHyLog(String key){

        try {
            return redisTemplate.opsForHyperLogLog().size(key);
        } catch (Exception e) {
            log.error("exception message", e);
            return 0;
        }

    }

    /**
     * @Description:  添加经纬度成员
     * @Author: lqy
     * @param key 键
     * @param longitude 经度
     * @param latitude 纬度
     * @param number 成员
     * @return: long

     */
    public long addGeo(String key,double longitude,double latitude ,String number){

        try {
            Long add = redisTemplate.opsForGeo().add(key, new Point(longitude, latitude), number);
            redisTemplate.opsForGeo().position(key,number);
            assert add != null;
            return add;
        } catch (Exception e) {
            log.error("exception message", e);
            return 0;
        }

    }

    /**
     * @Description: 添加bigMap
     * @Author: TOM
     * @Date: 2023/10/24 14:39
     * @param key s
     * @param offSize s
     * @param value s
     * @return java.lang.Boolean
     */
    public Boolean setBigMap(String key,Integer offSize,Boolean value){
        try {
            return this.redisTemplate.opsForValue().setBit(key, offSize, value);
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return false;
    }

    /**
     * @Description: 获取bigMap
     * @Author: TOM
     * @Date: 2023/10/24 14:39
     * @param key s
     * @param offSize s
     * @return java.lang.Boolean
     */
    public Boolean getBigMap(String key,Integer offSize){

        try {

            return this.redisTemplate.opsForValue().getBit(key, offSize);
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return false;
    }

    /**
     * @Description: 获取bigList
     * @Author: TOM
     * @Date: 2024/1/9 15:24
     * @param key 键
     * @param bitFieldSubCommands 位字段子命令
     * @return java.util.List<java.lang.Long>
     */
    public List<Long> getBigList(String key, BitFieldSubCommands bitFieldSubCommands ){

        try {
            return redisTemplate.opsForValue().bitField(key, bitFieldSubCommands);
        } catch (Exception e) {
            log.error("exception message", e);
            return null;
        }
    }



//------------------------------------------------------Redis-Stream消息队列-------------------------------------------------------------------


    /**
     * 添加对象消息
     *
     * @param key 键
     * @param obj 值
     * @return 记录id
     */
    public RecordId xAdd(String key, Object obj) {
        final ObjectRecord<String, Object> record = StreamRecords.objectBacked(obj).withStreamKey(key);
        return this.redisTemplate.opsForStream().add(record);
    }

    /**
     * 添加map消息
     *
     * @param key 键
     * @param message 消息
     * @return 记录id
     */
    public RecordId addStream(String key, Map<String, Object> message) {
        return this.redisTemplate.opsForStream().add(key, message);
    }

    /**
     * 添加分组
     *
     * @param key 键
     * @param groupName 分组名称
     */
    public void addGroup(String key, String groupName) {
        this.redisTemplate.opsForStream().createGroup(key, groupName);
    }

    /**
     * 分组是否存在
     *
     * @param key 键
     * @param groupName 分组名称
     * @return boolean
     */
    public Boolean groupExists(String key, String groupName) {
        final StreamInfo.XInfoGroups groups = this.redisTemplate.opsForStream().groups(key);
        return groups.stream().anyMatch(b -> Objects.equals(groupName, b.groupName()));
    }

    /**
     * 消费确认
     *
     * @param key 键
     * @param group 分组
     * @param ids ids
     * @return long
     */
    public Long ack(String key, String group, RecordId... ids) {
        return this.redisTemplate.opsForStream().acknowledge(key, group, ids);
    }

    /**
     * 删除指定id的消息
     *
     * @param key 键
     * @param ids ids
     */
    public Long delField(String key, RecordId... ids) {
        if (ArrayUtil.isEmpty(ids)) {
            return 0L;
        }
        return this.redisTemplate.opsForStream().delete(key, ids);
    }

    /**
     * 获取未ack的消息列表
     *
     * @param key 键
     * @param group 分组
     * @param consumer 名称
     * @return 待处理邮件
     */
    public PendingMessages pendingMessages(String key, String group, String consumer) {
        return this.redisTemplate.opsForStream().pending(key, Consumer.from(group, consumer));
    }

    /**
     * 消息是否确认，判断pending列表中有没有该消息
     * @param key 键
     * @param group 分组
     * @param consumer 消费者
     * @param id id
     * @return true:已确认
     */
    public Boolean msgIsAck(String key, String group, String consumer, RecordId id) {
        final PendingMessages pending = this.redisTemplate.opsForStream().pending(key, Consumer.from(group, consumer), Range.rightOpen(id.toString(), id.toString()), -1);
        return pending.isEmpty();
    }

    /**
     * 消息是否确认，判断pending列表中有没有该消息
     * @param key 键
     * @param group 分组
     * @param consumer 消费者
     * @param ids ids
     * @return true:已确认
     */
    public Boolean msgIsAck(String key, String group, String consumer, RecordId... ids) {
        final PendingMessages pendingMessages = this.pendingMessages(key, group, consumer);
        if (pendingMessages == null || pendingMessages.isEmpty()) {
            return true;
        }
        final ArrayList<RecordId> recordIds = CollUtil.toList(ids);
        return pendingMessages.stream().noneMatch(b -> CollUtil.contains(recordIds, b.getId()));
    }

    /**
     * 获取消息
     *
     * @param key 键
     * @param range 范围
     */
    public List<MapRecord<String, Object, Object>> rangeMap(String key, Range<String> range) {
        return this.redisTemplate.opsForStream().range(key, range, RedisZSetCommands.Limit.unlimited());
    }

    /**
     * 获取消息
     *
     * @param cla c
     * @param key 键
     * @param range 范围
     */
    public <T> List<ObjectRecord<String, T>> rangeObj(Class<T> cla, String key, Range<String> range) {
        return this.redisTemplate.opsForStream().range(cla, key, range, RedisZSetCommands.Limit.unlimited());
    }
}
