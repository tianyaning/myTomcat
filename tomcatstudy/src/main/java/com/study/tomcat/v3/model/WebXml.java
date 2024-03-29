package com.study.tomcat.v3.model;

import javax.servlet.Servlet;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class WebXml {
    //项目文件地址
    public String projectPath = null;

    //servlet定义
    public Map<String, Object> servlets = new HashMap<String, Object>();

    //servlet映射
    public Map<String, String> servletMapping = new HashMap<String, String>();

    //servlet实例集合
    public Map<String, Servlet> servletInstances = new HashMap<String, Servlet>();

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public Map<String, Object> getServlets() {
        return servlets;
    }

    public void setServlets(Map<String, Object> servlets) {
        this.servlets = servlets;
    }

    public Map<String, String> getServletMapping() {
        return servletMapping;
    }

    public void setServletMapping(Map<String, String> servletMapping) {
        this.servletMapping = servletMapping;
    }

    public void loadServlet() throws Exception {
        //每个项目都需要一个类加载器，去加载指定位置的class信息
        URL classUrl = new URL("file://" + projectPath + "/WEB-INF/classes/");

        URLClassLoader servletClassLoader = new URLClassLoader(new URL[]{classUrl});

        //加载该项目中的所有servlet
        for (Map.Entry<?,?> entry : this.servlets.entrySet()) {
            String servletClassName = entry.getValue().toString();
            String servletName = entry.getKey().toString();


            //1、加载到jvm
            Class<?> servletClass = servletClassLoader.loadClass(servletClassName);
            System.out.println(servletClass.getName());

            //2、反射实例化
            Servlet o = (Servlet) servletClass.newInstance();

            //3、保存起来，以后要用
            servletInstances.put(servletName, o);

        }


    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WebXml{");
        sb.append("projectPath='").append(projectPath).append('\'');
        sb.append(", servlets=").append(servlets.toString());
        sb.append(", servletMapping=").append(servletMapping.toString());
        sb.append('}');
        return sb.toString();
    }

    public static void main(String[] args) throws Exception{
        URL classUrl = new URL("file:///Users/xiongjiyuan/tianyaning/myTomcat/webapps/servlet-demo-1.0-SNAPSHOT/WEB-INF/classes/");
        URLClassLoader servletClassLoader = new URLClassLoader(new URL[]{classUrl});
        servletClassLoader.loadClass("com.study.servlet.IndexServlet");
    }
}
