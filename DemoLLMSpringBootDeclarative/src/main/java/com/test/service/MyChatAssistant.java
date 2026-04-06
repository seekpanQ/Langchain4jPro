package com.test.service;


import dev.langchain4j.service.spring.AiService;

/**
 * 面向接口编程
 */
@AiService
public interface MyChatAssistant {

    String dochat(String userMessage);
}
