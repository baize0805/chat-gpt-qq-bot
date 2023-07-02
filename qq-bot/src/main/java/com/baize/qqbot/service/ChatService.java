package com.baize.qqbot.service;

/**
 * @author baize
 * @version 1.0
 * @date 2023/5/30 14:28
 */
public interface ChatService {
    /**
     * 获取问题的回答
     * @param message qq用户传来的消息
     * @return chatGPT生成的消息
     */
    String requestChatMessage(String message);
    String requestChatMessage(String id, String message);
}
