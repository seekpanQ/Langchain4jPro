package com.test.controller;

import dev.langchain4j.model.chat.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    // 自动注入我们配置的大模型啊
    @Autowired
    private ChatModel chatModel;

    @GetMapping(value = "/langchain4j/hello") // 请求路径
    @ResponseBody // 方法的返回值应该直接写入 HTTP 响应体中
    /*
    value = "question"：指定要从请求中获取名为 question 的参数
    defaultValue = "你是谁"：如果没有提供 question 参数，使用默认值 "你是谁"
    String question：将提取的参数值绑定到这个方法参数
     */
    public String hello(@RequestParam(value = "question", defaultValue = "你是谁") String question) {
        String result = chatModel.chat(question);
        System.out.println("调用大模型回复: " + result);
        return result;
    }


}
