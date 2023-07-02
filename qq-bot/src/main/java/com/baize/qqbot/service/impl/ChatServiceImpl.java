package com.baize.qqbot.service.impl;

import com.baize.qqbot.dao.MsgRedis;
import com.baize.qqbot.service.ChatService;
import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import com.plexpt.chatgpt.entity.chat.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author baize
 * @version 1.0
 * @date 2023/5/30 15:10
 */
@Service
public class ChatServiceImpl implements ChatService {

    @Value("${openai.openapi-key}")
    private String apiKey;
    @Value("${openai.apiUrl}")
    private String apiHost;

    @Autowired
    private MsgRedis msgRedis;

    Logger log = LoggerFactory.getLogger(ChatServiceImpl.class);

    @Override
    public String requestChatMessage(String message) {
        ChatGPT chatGPT = ChatGPT.builder()
                .apiKey(apiKey)
                .timeout(900)
                .apiHost(apiHost)
                .build()
                .init();
        Message system = Message.ofSystem("你现在是问答专家！");
        Message msg = Message.of(message);

        ChatCompletion chatCompletion = ChatCompletion.builder()
                .model(ChatCompletion.Model.GPT_3_5_TURBO.getName())
                .messages(Arrays.asList(system, msg))
                .maxTokens(3000)
                .temperature(0.9)
                .build();
        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        Message res = response.getChoices().get(0).getMessage();
        return res.getContent();
    }

    @Override
    public String requestChatMessage(String id, String message) {
        List<Message> messageList = msgRedis.getMsgList(id);
        ChatGPT chatGPT = ChatGPT.builder()
                .apiKey(apiKey)
                .timeout(900)
                .apiHost(apiHost)
                .build()
                .init();
        Message userMsg = Message.of(message);

        messageList.add(userMsg);
        ChatCompletion chatCompletion = ChatCompletion.builder()
                .model(ChatCompletion.Model.GPT_3_5_TURBO.getName())
                .messages(messageList)
                .maxTokens(3000)
                .temperature(0.9)
                .build();
        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        log.info(response.toString());
        Message res = response.getChoices().get(0).getMessage();
        msgRedis.addMsg(id, userMsg);
        msgRedis.addMsg(id, res);
        return res.getContent();
    }
}
