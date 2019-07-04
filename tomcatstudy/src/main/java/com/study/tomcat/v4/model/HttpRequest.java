package com.study.tomcat.v4.model;

import java.util.Hashtable;

public class HttpRequest {
    private  String requestLine;
    private  String requestMethod;
    private  String requestPath;
    private  String httpVersion;
    private  Hashtable<String, String> requestHeaders;
    private  byte[] messagetBody;

    public HttpRequest() {
    }

    public HttpRequest(String requestLine, String requestMethod, String requestPath, String httpVersion,
            Hashtable<String, String> requestHeaders, byte[] messagetBody) {
        this.requestLine = requestLine;
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
        this.httpVersion = httpVersion;
        this.requestHeaders = requestHeaders;
        this.messagetBody = messagetBody;
    }

    public String getRequestLine() {
        return requestLine;
    }

    public void setRequestLine(String requestLine) {
        this.requestLine = requestLine;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public Hashtable<String, String> getrequestHeaders() {
        return requestHeaders;
    }

    public void setrequestHeaders(Hashtable<String, String> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public byte[] getMessagetBody() {
        return messagetBody;
    }

    public void setMessagetBody(byte[] messagetBody) {
        this.messagetBody = messagetBody;
    }
}
