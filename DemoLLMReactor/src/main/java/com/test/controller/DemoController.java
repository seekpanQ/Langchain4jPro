package com.test.controller;

import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

// 该注解：将一个普通的 Java 类标识为 Spring MVC 的控制器（Controller）。
@Controller
public class DemoController {

    // 注入流式编程输出的配置对象
    @Autowired
    @Qualifier("qwenstream")
    private StreamingChatModel streamChatModel;

    @GetMapping(value = "/chatstream/chat")
    public Flux<String> chat(@RequestParam("prompt") String prompt) {
        return Flux.create(new Consumer<FluxSink<String>>() {
            @Override
            public void accept(FluxSink<String> stringFluxSink) {
                streamChatModel.chat(prompt, new StreamingChatResponseHandler() {
                    @Override
                    public void onPartialResponse(String partialResponse) {
                        stringFluxSink.next(partialResponse);
                    }

                    @Override
                    public void onCompleteResponse(ChatResponse chatResponse) {
                        stringFluxSink.complete();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        stringFluxSink.error(throwable);
                    }
                });
            }
        });

    }
}
