package com.test.controller;

import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Base64;

@Controller
public class DemoController {

    // 自动注入我们配置的大模型啊
    @Autowired
    @Qualifier("qwen")
    private ChatModel chatModelQwen;

    @Autowired
    @Qualifier("deepseek")
    private ChatModel chatModelDeepseek;
    @Value("classpath:static/images/test.png")
    private Resource resource;

    @GetMapping(value = "/langchain4j/qwen") // 请求路径
    @ResponseBody // 方法的返回值应该直接写入 HTTP 响应体中
    /*
    value = "question"：指定要从请求中获取名为 question 的参数
    defaultValue = "你是谁"：如果没有提供 question 参数，使用默认值 "你是谁"
    String question：将提取的参数值绑定到这个方法参数
     */
    public String qwenCall(@RequestParam(value = "question", defaultValue = "你是谁") String question) {
        String result = chatModelQwen.chat(question);
        System.out.println("调用大模型回复: " + result);
        return result;
    }

    @GetMapping(value = "/langchain4j/deepseek") // 请求路径
    @ResponseBody // 方法的返回值应该直接写入 HTTP 响应体中
    public String deepseekCall(@RequestParam(value = "question", defaultValue = "你是谁") String question) {
        String result = chatModelDeepseek.chat(question);
        System.out.println("调用大模型回复: " + result);
        return result;
    }

    @GetMapping(value = "/langchain4j/testimage")
    @ResponseBody
    public String readImageContent() throws IOException {

        String result = null;

        //第1步，图片转码：通过Base64编码将图片转化为字符串
        byte[] byteArray = resource.getContentAsByteArray();
        String base64Data = Base64.getEncoder().encodeToString(byteArray);

        //第2步，提示词指定：结合ImageContent和TextContent一起发送到模型进行处理。
        //在 LangChain4j 框架中，UserMessage 是封装用户输入内容的核心类
        UserMessage userMessage = UserMessage.from(
                TextContent.from("识别图片图片中文字"),
                ImageContent.from(base64Data, "image/png")
        );
        //第3步，API调用：使用ChatModel来构建请求，并通过chat()方法调用模型。
        //请求内容包括文本提示和图片(封装好的UserMessage对象)，模型会根据输入返回分析结果。
        ChatResponse chatResponse = chatModelQwen.chat(userMessage);

        //第4步，解析与输出：从ChatResponse中获取AI大模型的回复，打印出处理后的结果。
        result = chatResponse.aiMessage().text();

        //后台打印
        System.out.println(result);

        //返回前台
        return result;
    }


}
