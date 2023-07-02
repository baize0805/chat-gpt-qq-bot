package com.baize.qqbot.listener;

import com.baize.qqbot.service.ChatService;
import love.forte.simboot.annotation.ContentTrim;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.Listener;
import love.forte.simboot.filter.MatchType;
import love.forte.simbot.component.qguild.event.QGAtMessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author baize
 * @version 1.0
 * @date 2023/5/29 19:48
 */
@Component
public class AIListener {

    @Autowired
    private ChatService chatService;

    @Listener
    @Filter(value = "^/ai ", matchType = MatchType.REGEX_CONTAINS)
    @ContentTrim
    public void onChannelCommandMessage(QGAtMessageCreateEvent event) {
        String message = event.getMessageContent().getPlainText().substring(5);
        String id = event.getAuthor().getId().toString();
        String requestChatMessage = chatService.requestChatMessage(id, message);
        event.replyAsync(requestChatMessage);
    }
}
