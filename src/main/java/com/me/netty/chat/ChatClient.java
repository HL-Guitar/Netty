package com.me.netty.chat;

import com.me.netty.basic.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * ClassName: ChatClient
 *
 * @Author: Light
 * @Date: 2019/8/11 18:03
 * Description:
 */

public class ChatClient {
    private final int port;
    private final String host;

    public ChatClient(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public void run() {
        ChannelFuture cf = null;
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).
                    channel(NioSocketChannel.class).
                    handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc){
                            ChannelPipeline pipeline = sc.pipeline();
                            pipeline.addLast("decoder",new StringDecoder());
                            pipeline.addLast("encoder",new StringEncoder());
                            pipeline.addLast(new ChatClientHandler());
                        }
                    });

            System.out.println("####CLient is ready......");
            cf = bootstrap.connect("127.0.0.1", 9999).sync();
            Channel channel = cf.channel();
            System.out.println("--- "+channel.localAddress().toString()+"------");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                String msg = scanner.nextLine();
                channel.writeAndFlush(msg+"\n");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        new ChatClient(9999,"127.0.0.1").run();
    }
}