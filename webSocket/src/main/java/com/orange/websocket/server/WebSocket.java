package com.orange.websocket.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Li ZhiCheng
 * @create: 2022-11-2022/11/30 15:37
 * @description: WebSocket服务类
 */
//注册成组件
@Component
//定义websocket服务器端，它的功能主要是将目前的类定义成一个websocket服务器端。注解的值将被用于监听用户连接的终端访问URL地址
@ServerEndpoint(value = "/webSocket/{userId}")
@Slf4j
public class WebSocket {

    //实例一个session，这个session是websocket的session
    private Session session;


    //在线人数
    public static int onlineNumber = 0;

    //用户名称
    private String userId;

    //存放websocket的集合
    private static Map<String, WebSocket> clients = new ConcurrentHashMap<String, WebSocket>();

    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session)
    {
        onlineNumber++;
        log.info("现在来连接的客户id："+session.getId()+"，用户名："+userId);
        this.userId = userId;
        this.session = session;
        try {
            //messageType 1代表上线 2代表下线 3代表在线名单 4代表普通消息
            //先给所有人发送通知，说我上线了
            Map<String,Object> map1 = new HashMap<>();
            map1.put("messageType",1);
            map1.put("userId",userId);
            sendMessageAll(JSON.toJSONString(map1),userId);

            //把自己的信息加入到map当中去
            clients.put(userId, this);
            //给自己发一条消息：告诉自己现在都有谁在线
            Map<String,Object> map2 = new HashMap<>();
            map2.put("messageType",3);
            Set<String> set = clients.keySet();
            map2.put("onlineUsers",set);
            sendMessageTo(JSON.toJSONString(map2),userId);
        }
        catch (IOException e){
            log.info(userId+"上线的时候通知所有人发生了错误");
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.info("服务端发生了错误"+error.getMessage());
        //error.printStackTrace();
    }
    /**
     * 连接关闭
     */
    @OnClose
    public void onClose()
    {
        onlineNumber--;
        //webSockets.remove(this);
        clients.remove(userId);
        try {
            //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
            Map<String,Object> map1 = new HashMap<>();
            map1.put("messageType",2);
            map1.put("onlineUsers",clients.keySet());
            map1.put("userId",userId);
            sendMessageAll(JSON.toJSONString(map1),userId);
        }
        catch (IOException e){
            log.info(userId+"下线的时候通知所有人发生了错误");
        }
        //log.info("有连接关闭！ 当前在线人数" + onlineNumber);
        log.info("有连接关闭！ 当前在线人数" + clients.size());
    }

    /**
     * 收到客户端的消息
     *
     * @param message 消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session)
    {
        try {
            log.info("来自客户端消息：" + message+"，客户端的id是："+session.getId());
            System.out.println("------------  :"+message);
            JSONObject jsonObject = JSON.parseObject(message);
            String textMessage = jsonObject.getString("message");
            String fromUserId = jsonObject.getString("userId");
            String toUserId = jsonObject.getString("to");
            //如果不是发给所有，那么就发给某一个人
            //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
            Map<String,Object> map1 = new HashMap<>();
            map1.put("messageType",4);
            map1.put("textMessage",textMessage);
            map1.put("fromUserId",fromUserId);
            if(toUserId.equals("All")){
                map1.put("toUserId","所有人");
                sendMessageAll(JSON.toJSONString(map1),fromUserId);
            }
            else{
                map1.put("toUserId",toUserId);
                System.out.println("开始推送消息给"+toUserId);
                sendMessageTo(JSON.toJSONString(map1),toUserId);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            log.info("发生了错误了");
        }

    }


    public void sendMessageTo(String message, String ToUserId) throws IOException {
        for (WebSocket item : clients.values()) {


            //    System.out.println("在线人员名单  ："+item.userId.toString());
            if (item.userId.equals(ToUserId) ) {
                item.session.getAsyncRemote().sendText(message);

                break;
            }
        }
    }

    public void sendMessageAll(String message,String FromUserId) throws IOException {
        for (WebSocket item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineNumber;
    }

}
