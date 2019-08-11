package com.me.netty.chat;

import com.me.netty.basic.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * ClassName: NettyServer
 *
 * @Author: Light
 * @Date: 2019/8/10 20:56
 * Description:
 */

public class ChatServer {
    private int port;

    public ChatServer(int port){this.port=port;}

    public void run() {
        EventLoopGroup bossGroup = null;
        EventLoopGroup workGroup = null;
        try {
            bossGroup = new NioEventLoopGroup();
            workGroup = new NioEventLoopGroup();
            //3.创建服务器端启动助手
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workGroup).
                    channel(NioServerSocketChannel.class).
                    option(ChannelOption.SO_BACKLOG,128).
                    childOption(ChannelOption.SO_KEEPALIVE,true).
                    childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            ChannelPipeline pipeline = sc.pipeline();
                            pipeline.addLast("decoder",new StringDecoder());
                            pipeline.addLast("encoder",new StringEncoder());
                            pipeline.addLast(new ChatServerHandler());
                        }
                    });
            System.out.println("### Netty Server is ready.....");
            ChannelFuture cf = b.bind(port).sync();
            System.out.println("### Netty Server is starting.....");

            // 关闭通道，关闭线程组
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("###Server is ready end.....");
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            System.out.println("###Server is end.....");
        }

    }

    public static void main(String[] args) {
        new ChatServer(9999).run();
    }
}