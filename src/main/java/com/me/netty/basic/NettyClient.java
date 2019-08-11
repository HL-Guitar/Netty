package com.me.netty.basic;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * ClassName: NettyClient
 *
 * @Author: Light
 * @Date: 2019/8/10 20:56
 * Description:
 */

public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).
                channel(NioSocketChannel.class).
                handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new NettyClientHandler());
                    }
                });
        System.out.println("####CLient is ready......");
        ChannelFuture cf = b.connect("127.0.0.1", 9999).sync();

        cf.channel().closeFuture().sync();
        group.shutdownGracefully();

    }


}