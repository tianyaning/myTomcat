package com.study.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;

public class PostServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ServletInputStream inputstream = req.getInputStream();
        byte[] array = new byte[inputstream.available()];
        inputstream.read(array);

        //        String name = new String(req.getParameter("name"));
        //        String age = new String(req.getParameter("age"));
        String response = "";
        response += "HTTP/1.1 200 OK\r\n";
        response += "Content-Type: text/xml\r\n";
        response += "Content-Length: " + array.length + "\r\n";
        response += "\r\n";
        response += new String(array);
        //        response += "name = "+ name +"\n";
        //        response += "age = "+ age;
        System.out.println(response);
        resp.getOutputStream().write(response.getBytes());
        resp.getOutputStream().flush();
    }
}
