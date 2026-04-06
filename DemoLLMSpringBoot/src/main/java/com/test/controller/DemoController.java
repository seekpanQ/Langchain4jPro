package com.test.controller;

import dev.langchain4j.model.chat.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    @Autowired
    private ChatModel chatModelQwen;


    @GetMapping(value = "/langchain4j/qwen") // 请求路径
    @ResponseBody
    public String hello(@RequestParam(value = "question", defaultValue = "你是谁") String question) {
        System.out.println("走进了");
        String result = chatModelQwen.chat(question);
        System.out.println("调用大模型回复: " + result);
        return result;
    }
}
