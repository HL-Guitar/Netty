package com.me.netty.basic;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

//服务器端的业务处理类
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    //读取数据事件
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        System.out.println("NettyServerHandler:"+ctx);
        ByteBuf buf=(ByteBuf) msg;
        System.out.println("客户端发来的消息(NettyServerHandler)："+buf.toString(CharsetUtil.UTF_8));
    }

    //数据读取完毕事件
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.writeAndFlush(Unpooled.copiedBuffer("就是没钱",CharsetUtil.UTF_8));
    }

    //异常发生事件
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable t){
        ctx.close();
    }


}
