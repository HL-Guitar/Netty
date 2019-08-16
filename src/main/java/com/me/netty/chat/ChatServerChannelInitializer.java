package com.me.netty.chat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Description: 一个通道初始化对象
 * @Author: Light
 * @CreateDate: 2019/8/14$ 10:44$
 * @Version: 1.0
 */
public class ChatServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel sc) throws Exception {
        // 往Pipeline链中添加自定义的handler类
        ChannelPipeline pipeline=sc.pipeline();
        //往pipeline链中添加一个解码器
        pipeline.addLast("decoder",new StringDecoder());
        //往pipeline链中添加一个编码器
        pipeline.addLast("encoder",new StringEncoder());
        //往pipeline链中添加自定义的handler(业务处理类)
        pipeline.addLast(new ChatServerHandler());
        pipeline.addLast(new ChatServerOtherHandler());
    }
}
