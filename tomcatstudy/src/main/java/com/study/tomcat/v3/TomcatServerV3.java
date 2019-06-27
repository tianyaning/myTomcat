package com.study.tomcat.v3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TomcatServerV3 {
    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8088);
        System.out.println("tomcat服务器启动成功");

        while (!serverSocket.isClosed()) {
            //不断获取新连接
            final Socket request = serverSocket.accept();
            threadPool.execute(new Runnable() {
                public void run() {
                    try {
                        //接收数据&打印
                        InputStream inputStream = request.getInputStream();
                        System.out.println("收到请求：");
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                        String msg = null;
                        while ((msg = reader.readLine()) != null) {
                            if (msg.length() == 0) {
                                break;
                            }
                            System.out.println(msg);
                        }

                        OutputStream outputStream = request.getOutputStream();
                        outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
                        outputStream.write("Content-Length: 11\r\n\r\n".getBytes());
                        outputStream.write("Hello world".getBytes());
                        outputStream.flush();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            request.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            });
        }
    }
}
