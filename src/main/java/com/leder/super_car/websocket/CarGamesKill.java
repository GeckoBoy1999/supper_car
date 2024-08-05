package com.leder.super_car.websocket;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.leder.super_car.common.constant.RedisPrefixes;
import com.leder.super_car.common.utils.RedisUtil;
import com.leder.super_car.mapper.UserMapper;
import com.leder.super_car.websocket.entity.FleeKillExternalRole;
import com.leder.super_car.websocket.entity.FleeKillRole;
import com.leder.super_car.websocket.entity.FleeKillRoom;
import com.leder.super_car.websocket.utils.WR;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @className: CarGamesKill
 * @author: xiaomao
 * @date: 2024/7/28 17:44
 * @Version: 1.0
 * @description: 汽车逃杀
 */
@Slf4j
@Component
@ServerEndpoint(value = "/evadeKill/carGame")
public class CarGamesKill {





    /**
     * @Description: 用户注入
     * @Author: xiaomao
     * @Date: 2024/7/28 17:51
     * @param spCarUserMapper:
     */
    private static UserMapper userMapper;
    @Resource
    public void setSpCarUserMapper(UserMapper spCarUserMapper){
        CarGamesKill.userMapper =spCarUserMapper;
    }



    /**
     * @Description: redis注入
     * @Author: xiaomao
     * @Date: 2024/7/28 17:54
     * @param redisUtil:
     */
    private static RedisUtil redisUtil;
    @Resource
    public void setRedisUtil(RedisUtil redisUtil){
        CarGamesKill.redisUtil =redisUtil;
    }


    //session通道
    public static final Map<Long, Session> SESSION_MAP = new ConcurrentHashMap<>();

    //用户详细信息
    public static final Map<Long, FleeKillRole> ROLE_MAP = new ConcurrentHashMap<>();

    //对外用户信息
    public static final Map<Long, FleeKillExternalRole> EXTERNAL_ROLE_MAP = new ConcurrentHashMap<>();

    //房间信息
    public static final Map<Long, FleeKillRoom> KILL_ROOM_MAP = new ConcurrentHashMap<>();

    //上局攻击房间id
    public static String lastKillRoom = "2,5";

    //期数
    public static final AtomicInteger PERIODS_ID = new AtomicInteger(1);

    //下注人数
    private static final AtomicInteger ENTER_ROOM_GUY = new AtomicInteger(0);

    //总投注量
    private static final AtomicReference<BigDecimal> ALL_INPUT_SUM = new AtomicReference<>(BigDecimal.ZERO);

    //青铜皮肤免伤比例
    private static final BigDecimal BRONZE_RATIO = BigDecimal.valueOf(0.05);

    //白银皮肤免伤比例
    private static final BigDecimal SILVER_RATIO = BigDecimal.valueOf(0.1);

    //黄金皮肤免伤比例
    private static final BigDecimal GOLD_RATIO = BigDecimal.valueOf(0.2);




    /**
     * @Description: 建立连接
     * @Author: xiaomao
     * @Date: 2024/7/28 18:04
     * @param session:
     */
    @OnOpen
    public void onOpen(Session session) {
        log.info("websocket建立连接:大逃杀");
    }




    @OnMessage
    public void onMessage(String message, Session session) {
        JSONObject jsonObject = JSONObject.parseObject(message);
        log.info("大逃杀消息体：{}", jsonObject.toJSONString());

        String accessToken = jsonObject.getString("Authorization");
        Long userId = jsonObject.getLong("userId");

        if (StrUtil.isBlank(accessToken) || Objects.isNull(userId)){
            this.broadcast(session, WR.success("请先登录", "errorMsg"));
            return;
        }
//
//        if (!StpUtil.isLogin()) {
//            this.broadcast(session, WR.success("授权过期", "errorMsg"));
//            log.info("大逃杀token信息异常: {}", userId);
//            return;
//        }

        Integer type = jsonObject.getInteger("type");
        if (Objects.isNull(type)){
            return;
        }
        switch (type) {
            case 0: //初始化
                connectGame(userId,session);
                break;
//            case 1: //选择房间
//                enterRoom(userId,jsonObject.getInteger("roomId"),session);
//                break;
//            case 2: //下注
//                insertCoin(userId,jsonObject.getBigDecimal("pourCount"),session);
//                break;
//            case 3: //换房间
//                exchangeRoom(userId,jsonObject.getInteger("roomId"),session);
//                break;
//            case 4:
//                getRoomDetails(session);
//                break;
//            case 5:
//                getBalance(userId,session);
//                break;
            default:
                break;
        }
    }


    /**
     * @Description: 初始化用户数据
     * @Author: xiaomao
     * @Date: 2024/7/28 20:56
     * @param fleeKillRole:
     * @param session:
     * @param userId:
     */
    private void initData(FleeKillRole fleeKillRole, Session session, Long userId){
        Map<String, Object> map = new HashMap<>();
        map.put("fleeKillRole", fleeKillRole);
        map.put("countDown", redisUtil.getExpire(RedisPrefixes.COUNT_DOWN));
        map.put("externalRoles", new ArrayList<>(EXTERNAL_ROLE_MAP.values()));
        map.put("fleeKillRooms", new ArrayList<>(KILL_ROOM_MAP.values()));
        map.put("lastKillRoom", lastKillRoom);
        map.put("enterRoomGuy", ENTER_ROOM_GUY.get());
        int attackId = PERIODS_ID.get();

        if (0 == attackId){
//            Integer gamePeriods;
//            TsFleeAttackRecord tsFleeAttackRecord = tsFleeAttackRecordMapper.selectAttackIdIsNull();
//            if (null == tsFleeAttackRecord){
//                gamePeriods = tsFleeAttackRecordMapper.selectAttackCountByType() + 1;
//                tsFleeAttackRecord = new TsFleeAttackRecord();
//                tsFleeAttackRecord.setAttackId(gamePeriods);
//                tsFleeAttackRecordMapper.insert(tsFleeAttackRecord);
//            }else {
//                gamePeriods = tsFleeAttackRecord.getAttackId();
//            }
//            PERIODS_ID.set(gamePeriods);
//            map.put("periods", gamePeriods);
        }else {
            map.put("periods", attackId);
        }

        this.broadcast(session, WR.success(map, "initialData"));
        SESSION_MAP.put(userId, session);
    }


    /**
     * @Description: 广播所以用户
     * @Author: xiaomao
     * @Date: 2024/7/28 18:14
     * @param stringSessionMap:
     * @param wr:
     */
    private void broadcast(Map<Integer, Session> stringSessionMap, WR<Object> wr){
        String jsonString = JSONObject.toJSONString(wr);
        for (Map.Entry<Integer, Session> sessionEntry : stringSessionMap.entrySet()) {
            Session session = sessionEntry.getValue();
            try {
                if (session.isOpen()) {
                    synchronized (session) {
                        session.getBasicRemote().sendText(jsonString);
                    }
                }
            } catch (Exception e) {
                log.info("大逃杀未参与游戏用户广播异常: {}", e.getMessage());
            }
        }
    }

    /**
     * @Description: 广播所有用户
     * @Author: xiaomao
     * @Date: 2024/7/28 18:19
     * @param wr:
     */
    private void broadcast(WR<Object> wr){
        String jsonString = JSONObject.toJSONString(wr);
        for (Map.Entry<Long, Session> m : SESSION_MAP.entrySet()) {
            Session session = m.getValue();
            try {
                if (session.isOpen()) {
                    synchronized (session) {
                        session.getBasicRemote().sendText(jsonString);
                    }
                }
            } catch (Exception e) {
                log.info("(困难)大逃杀全局广播异常: {}", e.getMessage());
            }
        }
    }


    /**
     * @Description: 个人广播
     * @Author: xiaomao
     * @Date: 2024/7/28 18:19
     * @param session:
     * @param wr:
     */
    private void broadcast(Session session, WR<Object> wr){
        try {
            if (session.isOpen()) {
                synchronized (this) {
                    session.getBasicRemote().sendText(JSONObject.toJSONString(wr));
                }
            }
        } catch (Exception e) {
            log.info("(困难)大逃杀个人广播异常: {}", e.getMessage());
        }
    }




    /**
     * @Description: 个人广播
     * @Author: xiaomao
     * @Date: 2024/7/28 18:20
     * @param userId:
     * @param wr:
     */
    private void broadcast(Long userId, WR<Object> wr){
        Session session = SESSION_MAP.get(userId);
        this.broadcast(session, wr);
    }


    /**
     * @Description: 配置用户昵称
     * @Author: xiaomao
     * @Date: 2024/7/28 20:52
     * @param original:
     * @return java.lang.String
     */
    public static String maskString(String original) {
        String before = StringUtils.left(original,1);
        String after = StringUtils.right(original,2);
        return before + "****" + after;
    }


    /**
     * @Description: 连接游戏
     * @Author: xiaomao
     * @Date: 2024/7/28 20:48
     * @param userId:
     * @param session:
     */
    private void connectGame(Long userId,Session session){
        BigDecimal userBalance = userMapper.selectUserCoinBalance(userId);

        BigDecimal zero0 = BigDecimal.ZERO;

        //Integer skinGrade = tsUserWeekSkinMapper.selectUserSkinGrade(userId, TimeUtils.thisMonday());

        String nickname = maskString(userId.toString());


        if (ROLE_MAP.containsKey(userId)) {
            Session oleSession = SESSION_MAP.get(userId);
            if (!session.getId().equals(oleSession.getId())){
                broadcast(oleSession,WR.success("您已被迫下线","tapeMsg"));
            }
            log.info("初始化数据存在----");
            FleeKillRole fleeKillRole = ROLE_MAP.get(userId);
            fleeKillRole.setOwnCount(null == userBalance ? zero0 : userBalance);
            fleeKillRole.setUsername(nickname);
            fleeKillRole.setSkinGrade(1);
            log.info("(困难)初始化消息数据返回: {}",EXTERNAL_ROLE_MAP.values());
            this.initData(fleeKillRole, session, userId);
            return;
        }

        FleeKillRole fleeKillRole = new FleeKillRole();
        fleeKillRole.setUserId(userId);
        fleeKillRole.setOwnCount(null == userBalance ? zero0 : userBalance);
        fleeKillRole.setInputCount(zero0);
        fleeKillRole.setUsername(nickname);
        fleeKillRole.setSkinGrade(1);
        ROLE_MAP.put(userId, fleeKillRole);

        FleeKillExternalRole externalRole0 = new FleeKillExternalRole();
        externalRole0.setUserId(userId);
        externalRole0.setUsername(nickname);
        externalRole0.setIsRobot(false);
        externalRole0.setSkinGrade(1);

        log.info("初始化数据----");

        EXTERNAL_ROLE_MAP.put(userId, externalRole0);

        if (KILL_ROOM_MAP.isEmpty()) {
            Boolean ifAbsent = redisUtil.set("diffGoldCoinInit", 1, 3000L);
            if (Boolean.TRUE.equals(ifAbsent)){
                BigDecimal one = BigDecimal.ONE;
                for (long i = 1; i <= 6; i++) {
                    FleeKillRoom killRoom = new FleeKillRoom();
                    killRoom.setRoomId(i);
                    killRoom.setRoomInputSum(one);
                    KILL_ROOM_MAP.put(i, killRoom);
                }
                ALL_INPUT_SUM.updateAndGet(value -> value.add(new BigDecimal(6)));
            }
        }
        this.initData(fleeKillRole, session, userId);
        this.broadcast(WR.success(externalRole0, "insertExternalRole"));
        log.info("初始化消息数据返回: {}",userId);

    }


}
