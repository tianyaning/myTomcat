package com.study.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = new String(req.getParameter("name"));
        String age = new String(req.getParameter("age"));
        String response = "";
        response += "HTTP/1.1 200 OK\r\n";
        response += "name = "+ name +"\n";
        response += "age = "+ age;
        resp.getOutputStream().write(response.getBytes());
        resp.getOutputStream().flush();
    }
}
