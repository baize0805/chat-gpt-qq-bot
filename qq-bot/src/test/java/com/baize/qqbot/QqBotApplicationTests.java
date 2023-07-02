package com.baize.qqbot;

import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import com.plexpt.chatgpt.entity.chat.Message;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class QqBotApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        // Proxy proxy = Proxys.http("127.0.0.1", 10808);
        ChatGPT chatGPT = ChatGPT.builder()
                .apiKey("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")
                // .proxy(proxy)
                .timeout(900)
                .apiHost("https://apps.ichati.cn/553f745e-d435-411c-b1cb-9a348cc6d05a/") //反向代理地址
                .build()
                .init();

        Message system = Message.ofSystem("你现在是一个诗人，专门写七言绝句");
        Message message = Message.of("写一段七言绝句诗，题目是：火锅！");

        ChatCompletion chatCompletion = ChatCompletion.builder()
                .model(ChatCompletion.Model.GPT_3_5_TURBO.getName())
                .messages(Arrays.asList(system, message))
                .maxTokens(3000)
                .temperature(0.9)
                .build();
        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        Message res = response.getChoices().get(0).getMessage();
        System.out.println(res);
    }

}
