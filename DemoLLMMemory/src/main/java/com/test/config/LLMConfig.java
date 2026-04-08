package com.test.config;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Configuration
public class LLMConfig {

    @Bean("qwen")
    public ChatModel chatModelQwen() {

        return OpenAiChatModel.builder()
                .apiKey(System.getenv("qwenapikey"))// 从环境变量获取apikey
                .modelName("qwen3-max")// 模型名
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")// 调用地址
                .logRequests(true)// 日志级别设置为debug才有效
                .logResponses(true)
                .build();
    }

    /**
     * 对话记忆提供者：根据用户ID（memoryId）隔离对话记忆，使用线程安全的Map缓存记忆实例
     * 替代原 AI Services 中的 chatMemoryProvider(lambda)
     *
     * @return
     */
    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        // 用ConcurrentHashMap缓存不同用户的对话记忆，保证线程安全
        ConcurrentMap<Long, ChatMemory> memoryMap = new ConcurrentHashMap<>();

        return new ChatMemoryProvider() {
            @Override
            public ChatMemory get(Object memoryId) {
                Long userId = Long.valueOf(memoryId.toString());
                System.out.println(userId);
                // 不存在则创建新的MessageWindowChatMemory（最多100条消息）
                return memoryMap.computeIfAbsent(userId, id -> MessageWindowChatMemory.withMaxMessages(100));
            }
        };
    }
}
