package com.me.netty.basic;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * ClassName: NettyClientHandler
 *
 * @Author: Light
 * @Date: 2019/8/10 20:58
 * Description:
 */

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Clienrt: "+ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("老板还钱了", CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        System.out.println("服务器端发来消息："+buf.toString(CharsetUtil.UTF_8));
    }
}