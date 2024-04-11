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
import io.netty.handler.ssl.SslHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.cert.CertificateException;

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
                                    p.addLast(new SslHandler(prepareSSLContext()));
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

    private static SSLEngine prepareSSLContext() {
        String certFile = MyNettyServer.class.getClassLoader().getResource("keys/springboot.jks").getPath();
        try {
            String algorithm = Security.getProperty("ssl.KeyManagerFactory.algorithm");
            SSLContext sslContext = SSLContext.getInstance("TLS");
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
            KeyStore keyStore = KeyStore.getInstance("jks");
            keyStore.load(new FileInputStream(certFile), "password".toCharArray());
            kmf.init(keyStore, "password".toCharArray());
//            TrustManagerFactory tmf = TrustManagerFactory.getInstance("");
            sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());
            SSLEngine sslEngine = sslContext.createSSLEngine();
            sslEngine.setUseClientMode(false);
            sslEngine.setNeedClientAuth(false);
            return sslEngine;
        } catch (NoSuchAlgorithmException | KeyManagementException | UnrecoverableKeyException | KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
