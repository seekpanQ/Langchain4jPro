package com.test.controller;

import com.test.service.MyChatAssistant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    // 自动注入我们配置的大模型啊
    // 彻底脱离底层原生接口ChatModel了，这里注入我们自己的接口
    @Autowired
    private MyChatAssistant assistant;


    @GetMapping(value = "/langchain4j/qwen") // 请求路径
    @ResponseBody
    public String hello(@RequestParam(value = "question", defaultValue = "你是谁") String question) {
        System.out.println("走进了");
        String result = assistant.dochat(question);
        System.out.println("调用大模型回复: " + result);
        return result;
    }
}
