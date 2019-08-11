package com.me.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: ChatServerHandler
 *
 * @Author: Light
 * @Date: 2019/8/11 17:50
 * Description:
 */

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static List<Channel> channels = new ArrayList<Channel>();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        Channel inChannel = ctx.channel();
        for(Channel channel:channels){
            if(channel!=inChannel){
                channel.writeAndFlush("["+inChannel.remoteAddress().toString().substring(1)+"]"+"说："+s+"\n");

            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel inChannel = ctx.channel();
        channels.add(inChannel);
        System.out.println("[Server:]"+inChannel.remoteAddress().toString().substring(1)+"上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel inChannel = ctx.channel();
        channels.remove(inChannel);
        System.out.println("[Server:]"+inChannel.remoteAddress().toString().substring(1)+"离线");
    }
}