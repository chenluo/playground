/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package com.chenluo.server.inbound;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpHeaderValues.*;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class MyInboundHandler extends SimpleChannelInboundHandler<HttpObject> {
    Logger logger = LoggerFactory.getLogger(MyInboundHandler.class);
    HttpRequest request = null;

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        //        logger.warn("{}, {}", ctx, msg);
        if (msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;
            request = req;
            logger.info("{}", req);
        }
        if (msg instanceof HttpContent) {
            HttpContent httpContent = (HttpContent) msg;
            logger.info("httpcontent");

            if (msg instanceof LastHttpContent) {
                LastHttpContent trailer = (LastHttpContent) msg;
                logger.info("last httpcontent");
                boolean keepAlive = HttpUtil.isKeepAlive(request);
                FullHttpResponse httpResponse = new DefaultFullHttpResponse(HTTP_1_1,
                        ((HttpObject) trailer).decoderResult().isSuccess() ? OK : BAD_REQUEST,
                        Unpooled.copiedBuffer("response\r\n".toCharArray(), CharsetUtil.UTF_8));

                httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");

                if (keepAlive) {
                    httpResponse.headers().setInt(HttpHeaderNames.CONTENT_LENGTH,
                            httpResponse.content().readableBytes());
                    httpResponse.headers().set(HttpHeaderNames.CONNECTION,
                            HttpHeaderValues.KEEP_ALIVE);
                }
                ctx.write(httpResponse);

                if (!keepAlive) {
                    ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
                };
            }
        }
    }
}
