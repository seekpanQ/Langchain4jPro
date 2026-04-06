package com.test.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LLMConfig {

    /*
     将方法的返回值注册为Spring容器中的一个Bean
     ChatModel 所有大模型聊天的总的父接口

     该方法执行后：拿到这个返回的对象后，你就可以用它来调用其方法，实现与 AI 模型的对话，
     */
    @Bean("qwen")
    public ChatModel chatModelQwen() {

        return OpenAiChatModel.builder()
                .apiKey(System.getenv("qwenapikey"))// 从环境变量获取apikey
                .modelName("qwen3-max")// 模型名
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")// 调用地址
                .build();
    }

    @Bean("deepseek")
    public ChatModel chatModelDeepseek() {

        return OpenAiChatModel.builder()
                .apiKey(System.getenv("deepseekapikey"))// 从环境变量获取apikey
                .modelName("deepseek-chat")// 模型名
                .baseUrl("https://api.deepseek.com/v1")// 调用地址
                .build();
    }
}
