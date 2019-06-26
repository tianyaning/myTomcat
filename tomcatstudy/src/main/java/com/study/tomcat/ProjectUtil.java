package com.study.tomcat;

import java.io.File;
import java.io.FileFilter;

public class ProjectUtil {
    public void load() {
        //TODO 路径需要自己补充
        String webapps = "";
        // 0,war包自动解压

        //1,webapps子文件夹算是一个项目
        File[] projects = new File(webapps).listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });

        //
    }
}
