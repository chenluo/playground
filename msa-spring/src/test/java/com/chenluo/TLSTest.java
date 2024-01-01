package com.chenluo;

import org.junit.jupiter.api.Test;

import java.io.*;

import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class TLSTest {

    @Test
    public void testBlockingTlsClient() throws IOException {
        byte[] bytes;
        try (SSLSocket socket =
                (SSLSocket) SSLSocketFactory.getDefault().createSocket("www.google.com", 443)) {
            socket.setSSLParameters(
                    new SSLParameters(
                            new String[] {"TLS_AES_256_GCM_SHA384"}, new String[] {"TLSv1.3"}));
            socket.startHandshake();
            OutputStream outputStream = socket.getOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            PrintWriter printWriter = new PrintWriter(bufferedOutputStream);
            printWriter.println("GET / HTTP/1.1");
            printWriter.println();
            printWriter.flush();
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String l = null;
            while ((l = bufferedReader.readLine()) != null) {
                System.out.println(l);
                if (l.equals("0")) {
                    break;
                }
            }
            bufferedReader.close();
            inputStreamReader.close();
            printWriter.close();
        }
    }
}
