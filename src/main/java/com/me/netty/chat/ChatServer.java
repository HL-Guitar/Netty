package com.me.netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpChannel;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

//聊天程序服务器端
public class ChatServer {

    private int port; //服务器端端口号

    public ChatServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128) //设置bossGroup
                    .childOption(ChannelOption.SO_KEEPALIVE, true) //设置workerGroup，可以拓展到 attr,childattr
                    .childHandler(new ChatServerChannelInitializer());
            System.out.println("Netty Chat Server启动......");
            ChannelFuture f = b.bind(port).sync();
            if(f.isSuccess()){
                System.out.println("成功！！");
            }
            f.channel().closeFuture().sync();// 等待服务端监听端口关闭
        } finally {
            workerGroup.shutdownGracefully(); // 断开连接，关闭线程
            bossGroup.shutdownGracefully();
            System.out.println("Netty Chat Server关闭......");
        }
    }

    public static void main(String[] args) throws Exception {
        new ChatServer(9999).run();
    }
}