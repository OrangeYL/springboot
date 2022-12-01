package com.orange.websocket.controller;

import com.orange.common.utils.Result;
import com.orange.websocket.server.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author: Li ZhiCheng
 * @create: 2022-11-2022/11/30 15:42
 * @description: 服务端给客户端发消息
 */
@RestController
public class TestController {

    @Autowired
    private WebSocket webSocket;

    //单发
    @GetMapping("/sendTo")
    public Result<?> sendTo(@RequestParam("userId") String userId, @RequestParam("msg") String msg) throws IOException {
        webSocket.sendMessageTo(msg,userId);
        return Result.success();
    }
    //群发
    @GetMapping("/sendAll")
    public Result<?> sendAll(@RequestParam("msg") String msg) throws IOException {
        String fromUserId="system";//其实没用上
        webSocket.sendMessageAll(msg,fromUserId);
        return Result.success();
    }
}
