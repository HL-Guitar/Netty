package com.me.netty.basic;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * ClassName: NettyServer
 *
 * @Author: Light
 * @Date: 2019/8/10 20:56
 * Description:
 */

public class NettyServer {
    public static void main(String[] args) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        //3.创建服务器端启动助手
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup,workGroup).
                channel(NioServerSocketChannel.class).
                option(ChannelOption.SO_BACKLOG,128).
                childOption(ChannelOption.SO_KEEPALIVE,true).
                childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new NettyServerHandler());
                    }
                });
        System.out.println("###Server is ready.....");
        ChannelFuture cf = b.bind(9999).sync();
        System.out.println("###Server is starting.....");

        // 关闭通道，关闭线程组
        cf.channel().closeFuture().sync();
        System.out.println("###Server is ready end.....");
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
        System.out.println("###Server is end.....");

    }
}