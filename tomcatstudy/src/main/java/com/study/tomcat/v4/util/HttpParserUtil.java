package com.study.tomcat.v4.util;

import com.study.tomcat.v4.model.HttpRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

public class HttpParserUtil {


    /**
     * Parse and HTTP request.
     *
     * @param request
     *            String holding http request.
     * @throws IOException
     *             If an I/O error occurs reading the input stream.
     * @throws Exception
     *             If HTTP Request is malformed
     */
    public static HttpRequest parseRequest(InputStream request) throws IOException, Exception {
        HttpRequest httpRequest = new HttpRequest();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(request));

        //解析httpline
        String requestLine = doReadLine(request);
        if(requestLine != null) {
            String[] requestLineArray = requestLine.split(" ");
            String requestMethod = "";
            String requestPath = "";
            String httpVersion = "";
            if (requestLineArray.length == 3) {
                requestMethod = requestLineArray[0];
                requestPath = requestLineArray[1];
                httpVersion = requestLineArray[2];
            }

            //解析requestHeaders
            Hashtable<String, String> requestHeaders = new Hashtable<String, String>();
            String header = doReadLine(request);
            //读到header和消息体中间的空行中止，空行只有一个回车，所以应该是header.length() > 0 的条件不满足，应该是=0
            while (header != null && header.length() > 0) {
                int idx = header.indexOf(":");
                if (idx == -1) {
                    throw new Exception("Invalid Header Parameter: " + header);
                }
                requestHeaders.put(header.substring(0, idx), header.substring(idx + 1, header.length()));
                header = doReadLine(request);
            }

            //获取消息主题的长度
            int bodyContentLength = 0;
            if (requestHeaders.get("Content-Length") != null) {
                bodyContentLength = Integer.parseInt(requestHeaders.get("Content-Length").trim());
            }

            if (requestHeaders.get("content-length") != null) {
                bodyContentLength = Integer.parseInt(requestHeaders.get("content-length").trim());
            }

            httpRequest.setRequestLine(requestLine);
            httpRequest.setRequestMethod(requestMethod);
            httpRequest.setRequestPath(requestPath);
            httpRequest.setHttpVersion(httpVersion);
            httpRequest.setrequestHeaders(requestHeaders);

            if(bodyContentLength != 0) {
                //没有消息体，不用读取
                byte[] body = new byte[bodyContentLength];
                //解析消息主体
                request.read(body);
                httpRequest.setMessagetBody(body);
            }
            return httpRequest;
        } else {
            return null;
        }
    }

    /**
     * inputStream实现readline
     * @param stream
     * @return
     * @throws Exception
     */
    public static String doReadLine(InputStream stream) throws Exception {
        StringBuilder s = new StringBuilder();
        char cur;
        do {
            cur = (char) stream.read();
            if (cur == '\r' && (char)stream.read() == '\n')
                break;
            s.append(cur);
        } while (cur != -1);
        return s.toString();
    }
}
