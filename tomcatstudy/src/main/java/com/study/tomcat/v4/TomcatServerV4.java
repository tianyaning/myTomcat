package com.study.tomcat.v4;

import com.study.tomcat.v4.model.WebXml;
import com.study.tomcat.v4.util.ProjectUtil;
import com.study.tomcat.v4.model.HttpRequest;
import com.study.tomcat.v4.util.HttpParserUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TomcatServerV4 {
    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    public static void main(String[] args) throws Exception {
        // 启动之前要加载项目信息
        final Map<String, WebXml> configInfos = ProjectUtil.load();

        final ServerSocket serverSocket = new ServerSocket(8088);
        System.out.println("tomcat服务器启动成功");

        while (!serverSocket.isClosed()) {
            //不断获取新连接
            final Socket request = serverSocket.accept();
            threadPool.execute(new Runnable() {
                public void run() {
                    try {
                        HttpRequest httpRequest = HttpParserUtil.parseRequest(request.getInputStream());
                        if (httpRequest != null) {
                            String requestPath = httpRequest.getRequestPath();
                            String path = requestPath.substring(1, requestPath.length());
                            String projectName = path.split("/")[0];
                            String projectPath = path.substring(projectName.length(), path.length());

                            //根据请求，去找到对应的项目，并且调用相关servlet
                            //根据url请求调用servlet的方法
                            WebXml webXml = configInfos.get(projectName);
                            if(webXml != null) {
                                //解析项目名称:
                                String servletName = webXml.servletMapping.get(projectPath);
                                //解析请求servlet路径:
                                Servlet servlet = webXml.servletInstances.get(servletName);

                                //Servlet生命周期，调用doGet,doPost方法就会触发service方法。
                                HttpServletRequest servletRequest = createRequest(httpRequest.getRequestMethod(), request, httpRequest.getMessagetBody());
                                HttpServletResponse servletResponse = createResponse(request);
                                //真正触发执行
                                servlet.service(servletRequest, servletResponse);
                            }
                        }
                    } catch (Exception e) {
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

    private static HttpServletRequest createRequest(final String method, final Socket request, final byte[] messageBody) {
        return new HttpServletRequest() {
            public String getAuthType() {
                return null;
            }

            public Cookie[] getCookies() {
                return new Cookie[0];
            }

            public long getDateHeader(String s) {
                return 0;
            }

            public String getHeader(String s) {
                return null;
            }

            public Enumeration<String> getHeaders(String s) {
                return null;
            }

            public Enumeration<String> getHeaderNames() {
                return null;
            }

            public int getIntHeader(String s) {
                return 0;
            }

            public String getMethod() {
                //根据用户请求信息去解析
                return method;
            }

            public String getPathInfo() {
                return null;
            }

            public String getPathTranslated() {
                return null;
            }

            public String getContextPath() {
                return null;
            }

            public String getQueryString() {
                return null;
            }

            public String getRemoteUser() {
                return null;
            }

            public boolean isUserInRole(String s) {
                return false;
            }

            public Principal getUserPrincipal() {
                return null;
            }

            public String getRequestedSessionId() {
                return null;
            }

            public String getRequestURI() {
                return null;
            }

            public StringBuffer getRequestURL() {
                return null;
            }

            public String getServletPath() {
                return null;
            }

            public HttpSession getSession(boolean b) {
                return null;
            }

            public HttpSession getSession() {
                return null;
            }

            public boolean isRequestedSessionIdValid() {
                return false;
            }

            public boolean isRequestedSessionIdFromCookie() {
                return false;
            }

            public boolean isRequestedSessionIdFromURL() {
                return false;
            }

            public boolean isRequestedSessionIdFromUrl() {
                return false;
            }

            public boolean authenticate(HttpServletResponse httpServletResponse) throws IOException, ServletException {
                return false;
            }

            public void login(String s, String s1) throws ServletException {

            }

            public void logout() throws ServletException {

            }

            public Collection<Part> getParts() throws IOException, ServletException {
                return null;
            }

            public Part getPart(String s) throws IOException, ServletException {
                return null;
            }

            public Object getAttribute(String s) {
                return null;
            }

            public Enumeration<String> getAttributeNames() {
                return null;
            }

            public String getCharacterEncoding() {
                return null;
            }

            public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

            }

            public int getContentLength() {
                return messageBody.length;
            }

            public String getContentType() {
                return null;
            }

            public ServletInputStream getInputStream() throws IOException {
                return new ServletInputStream() {
                    @Override
                    public int read() throws IOException {
                        return request.getInputStream().read();
                    }
                };
            }

            public String getParameter(String s) {
                return null;
            }

            public Enumeration<String> getParameterNames() {
                return null;
            }

            public String[] getParameterValues(String s) {
                return new String[0];
            }

            public Map<String, String[]> getParameterMap() {
                return null;
            }

            public String getProtocol() {
                return null;
            }

            public String getScheme() {
                return null;
            }

            public String getServerName() {
                return null;
            }

            public int getServerPort() {
                return 0;
            }

            public BufferedReader getReader() throws IOException {
                return null;
            }

            public String getRemoteAddr() {
                return null;
            }

            public String getRemoteHost() {
                return null;
            }

            public void setAttribute(String s, Object o) {

            }

            public void removeAttribute(String s) {

            }

            public Locale getLocale() {
                return null;
            }

            public Enumeration<Locale> getLocales() {
                return null;
            }

            public boolean isSecure() {
                return false;
            }

            public RequestDispatcher getRequestDispatcher(String s) {
                return null;
            }

            public String getRealPath(String s) {
                return null;
            }

            public int getRemotePort() {
                return 0;
            }

            public String getLocalName() {
                return null;
            }

            public String getLocalAddr() {
                return null;
            }

            public int getLocalPort() {
                return 0;
            }

            public ServletContext getServletContext() {
                return null;
            }

            public AsyncContext startAsync() throws IllegalStateException {
                return null;
            }

            public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
                return null;
            }

            public boolean isAsyncStarted() {
                return false;
            }

            public boolean isAsyncSupported() {
                return false;
            }

            public AsyncContext getAsyncContext() {
                return null;
            }

            public DispatcherType getDispatcherType() {
                return null;
            }
        };
    }

    private static HttpServletResponse createResponse(final Socket request) {
        return new HttpServletResponse() {
            public void addCookie(Cookie cookie) {

            }

            public boolean containsHeader(String s) {
                return false;
            }

            public String encodeURL(String s) {
                return null;
            }

            public String encodeRedirectURL(String s) {
                return null;
            }

            public String encodeUrl(String s) {
                return null;
            }

            public String encodeRedirectUrl(String s) {
                return null;
            }

            public void sendError(int i, String s) throws IOException {

            }

            public void sendError(int i) throws IOException {

            }

            public void sendRedirect(String s) throws IOException {

            }

            public void setDateHeader(String s, long l) {

            }

            public void addDateHeader(String s, long l) {

            }

            public void setHeader(String s, String s1) {

            }

            public void addHeader(String s, String s1) {

            }

            public void setIntHeader(String s, int i) {

            }

            public void addIntHeader(String s, int i) {

            }

            public void setStatus(int i) {

            }

            public void setStatus(int i, String s) {

            }

            public int getStatus() {
                return 0;
            }

            public String getHeader(String s) {
                return null;
            }

            public Collection<String> getHeaders(String s) {
                return null;
            }

            public Collection<String> getHeaderNames() {
                return null;
            }

            public String getCharacterEncoding() {
                return null;
            }

            public String getContentType() {
                return null;
            }

            public ServletOutputStream getOutputStream() throws IOException {
                return new ServletOutputStream() {
                    @Override
                    public void write(int b) throws IOException {
                        request.getOutputStream().write(b);
                    }
                };
            }

            public PrintWriter getWriter() throws IOException {
                return null;
            }

            public void setCharacterEncoding(String s) {

            }

            public void setContentLength(int i) {

            }

            public void setContentType(String s) {

            }

            public void setBufferSize(int i) {

            }

            public int getBufferSize() {
                return 0;
            }

            public void flushBuffer() throws IOException {

            }

            public void resetBuffer() {

            }

            public boolean isCommitted() {
                return false;
            }

            public void reset() {

            }

            public void setLocale(Locale locale) {

            }

            public Locale getLocale() {
                return null;
            }
        };
    }

}
