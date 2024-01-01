/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package com.chenluo.server;

import com.chenluo.server.inbound.MyInboundHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyNettyServer {

    private static Logger logger = LoggerFactory.getLogger(MyNettyServer.class);

    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup(10);
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(
                            new ChannelInitializer<NioSocketChannel>() {
                                @Override
                                protected void initChannel(NioSocketChannel ch) throws Exception {
                                    ChannelPipeline p = ch.pipeline();
                                    p.addLast(new HttpServerCodec());
                                    p.addLast(new HttpServerExpectContinueHandler());
                                    p.addLast(new MyInboundHandler());
                                }
                            });
            Channel channel =
                    server.bind(8880)
                            .addListener(
                                    future -> {
                                        if (future.isSuccess()) {
                                            logger.info("successfully bind");
                                        } else {
                                            logger.error("failed to bind");
                                        }
                                    })
                            .channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("error.", e);
            Thread.currentThread().interrupt();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
