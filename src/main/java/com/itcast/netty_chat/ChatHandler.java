package com.itcast.netty_chat;

import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @Author: Light
 * @CreateDate: 2019/7/29$ 19:45$
 * @Version: 1.0
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //用来保存客户端的连接
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private SimpleDateFormat sdf =new SimpleDateFormat("yyyy-mm-dd hh:MM");

    //当channel中有新的事件消息会自动调用
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 当接收到数据后会自动调用
        String text = msg.text();
        System.out.println("接收到的消息为："+text);
        for(Channel channel:clients){
            //将消息发送给所有客户端
            channel.writeAndFlush(new TextWebSocketFrame(sdf.format(new Date()) + ":" + text));
        }
    }

    // 当有新的客户端连接服务器之后，会自动调用这个方法
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // 将新的通道加入到clients
        clients.add(ctx.channel());
    }
}
