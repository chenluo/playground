package com.chenluo;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class VanillaEchoServer {
    public static void main(String[] args) throws IOException {
        new VanillaEchoServer().start();
    }

    void start() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.configureBlocking(false);
        SocketAddress address = new InetSocketAddress(8888);
        serverSocket.bind(address);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        int readReadyCount = 0;

        while (true) {
            if (selector.select() != 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        try {
                            SocketChannel client = serverSocket.accept();
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);
                        } catch (Exception e) {
                            System.out.println("accept");
                            System.out.println(e);
                        }
                    }
                    if (key.isReadable()) {
                        readReadyCount++;
                        SocketChannel client = null;
                        try {
                            client = (SocketChannel) key.channel();

                            ByteBuffer byteBuffer = ByteBuffer.allocate(100);
                            while (client.read(byteBuffer) > 0) {
                                System.out.println(byteBuffer.array());
                                byteBuffer.flip();
                                client.write(byteBuffer);
                                byteBuffer.clear();
                            }
//                            client.write(ByteBuffer.allocate(100).get("Hello".getBytes(StandardCharsets.UTF_8)));
                            client.close();

                        } catch (Exception e) {
                            System.out.println("read ready for: " + readReadyCount);
                            System.out.println(e.toString());
                            if (client != null) {
                                client.close();
                            }

                        }
                    }
                    iterator.remove();
                }
            }
        }
    }
}
