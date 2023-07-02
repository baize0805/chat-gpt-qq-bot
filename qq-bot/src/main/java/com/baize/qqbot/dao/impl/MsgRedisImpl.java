package com.baize.qqbot.dao.impl;

import com.alibaba.fastjson2.JSON;
import com.baize.qqbot.dao.MsgRedis;
import com.plexpt.chatgpt.entity.chat.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author baize
 * @version 1.0
 * @date 2023/6/2 10:50
 */
@Repository
public class MsgRedisImpl implements MsgRedis {

    @Autowired
    private StringRedisTemplate redisTemplate;

    Logger log = LoggerFactory.getLogger(MsgRedisImpl.class);

    @Override
    public List<Message> getMsgList(String id) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        List<String> msgList = listOps.range(id, 0, -1);
        log.info(msgList.toString());
        List<Message> messages = new ArrayList<>();
        for (String msg : msgList) {
           messages.add(JSON.parseObject(msg,Message.class));
        }
        log.info(messages.toString());
        return messages;
    }

    @Override
    public void addMsg(String id,Message msg) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        String jsonString = JSON.toJSONString(msg);
        Long aLong = listOps.rightPush(id, jsonString);
        log.info(jsonString);
        log.info(aLong.toString());
    }
}
