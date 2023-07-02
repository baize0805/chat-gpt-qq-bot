package com.baize.qqbot.listener;

import love.forte.simboot.annotation.ContentTrim;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.Listener;
import love.forte.simbot.component.qguild.event.QGAtMessageCreateEvent;
import org.springframework.stereotype.Component;

/**
 * @author baize
 * @version 1.0
 * @date 2023/5/29 19:18
 */
@Component
public class DemoListener {
    @Listener
    // @Filter(value = "你好", targets = @Filter.Targets(atBot = true))
    @Filter(value = "你好")
    @ContentTrim // 当匹配被at时，将'at'这个特殊消息移除后，剩余的文本消息大概率存在前后空格，通过此注解在匹配的时候忽略前后空格
    public void onChannelMessage(QGAtMessageCreateEvent event) { // 将要监听的事件类型放在参数里，即代表监听此类型的消息
        // Java中的阻塞式API
        System.out.println(event.getAuthor());
        System.out.println(event.getChannel());
        event.replyAsync("你也好!");
    }
}
