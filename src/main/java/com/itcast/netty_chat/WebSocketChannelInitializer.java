package com.itcast.netty_chat;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Description: 通道初始化器，用来加载ChatHandler
 * @Author: Light
 * @CreateDate: 2019/7/29$ 19:29$
 * @Version: 1.0
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    //初始化通道
    //在这个方法中加载对应的Handler
    protected void initChannel(SocketChannel ch) throws Exception {
        //获取管道，将一个一个的ChannelHandler添加到管道中
        ChannelPipeline pipeline = ch.pipeline();
        //添加一个http的编解码器
        pipeline.addLast(new HttpServerCodec());
        //添加一个用于支持大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 添加一个聚合器，这个聚合器主要是将HttpMessage聚合成FullHttpRequest/Response
        pipeline.addLast(new HttpObjectAggregator(1024*64));

        //需要指定接收请求的路由
        //只有/ws后缀结尾的才可以访问
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        //添加自定义的 Handler
        pipeline.addLast(new ChatHandler());
    }
}
