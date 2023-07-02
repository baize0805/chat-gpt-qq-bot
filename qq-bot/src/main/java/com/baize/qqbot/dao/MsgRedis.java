package com.baize.qqbot.dao;

import com.plexpt.chatgpt.entity.chat.Message;

import java.util.List;

/**
 * @author baize
 * @version 1.0
 * @date 2023/6/2 10:48
 */
public interface MsgRedis {

    List<Message> getMsgList(String id);

    void addMsg(String id,Message msg);
}
