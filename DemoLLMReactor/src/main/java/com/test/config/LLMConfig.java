package com.test.config;

import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LLMConfig {


    /**
     * ChatModel普通对话接口
     * StreamingChatModel 流式对话接口，也是LangChain4j 的核心接口
     *
     * @return
     */
    @Bean(name = "qwenstream")
    public StreamingChatModel streamChatModelQwen() {
        return OpenAiStreamingChatModel.builder()
                .apiKey(System.getenv("qwenapikey")) // 从环境变量获取apikey
                .modelName("qwen3-max") // 模型名
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")  // 调用地址
                .logRequests(true) // 日志级别设置为debug才有效
                .logResponses(true)// 日志级别设置为debug才有效
                .build();
    }
}
