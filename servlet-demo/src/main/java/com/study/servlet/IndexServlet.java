package com.study.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String response = "";
        response += "HTTP/1.1 200 OK\r\n";
        response += "Content-Length: 16\r\n\r\n";
        response += "Hello worldvvvvv";
        httpServletResponse.getOutputStream().write(response.getBytes());
        httpServletResponse.getOutputStream().flush();
    }

}
