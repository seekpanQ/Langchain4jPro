package com.test.controller;

import dev.langchain4j.chain.ConversationalChain;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    // 自动注入我们配置的大模型啊
    @Autowired
    @Qualifier("qwen")
    private ChatModel chatModelQwen;

    // 注入对话记忆提供者（按用户ID隔离记忆）
    @Autowired
    private ChatMemoryProvider chatMemoryProvider;


    /**
     * 原生方式实现 MessageWindow 对话记忆功能（与原接口逻辑完全一致）
     * 参数：用户id，用户问题
     */
    @GetMapping(value = "/chatmemory/test")
    @ResponseBody
    public String chatMessageWindowChatMemory(@RequestParam("userId") String userId,
                                              @RequestParam("question") String question) {

        System.out.println("userId" + userId);


        // 1. 根据用户ID获取专属的对话记忆
        ChatMemory memory1 = chatMemoryProvider.get(Long.parseLong(userId));
        // 2. 创建原生对话链：绑定模型和用户1的记忆
        ConversationalChain conversation1 = ConversationalChain.builder()
                .chatModel(chatModelQwen)
                .chatMemory(memory1)
                .build();
        // 3. 发送第一条消息："你好！我的名字是Java."
        String answer = conversation1.execute(question);
        return answer;

    }

}
