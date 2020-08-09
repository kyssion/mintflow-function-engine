package org.mintflow.util;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ResourceFindUtil {
    public static List<String> getFileListByPath(String path) throws IOException {
        List<String> fileNameList = new ArrayList<>();
        URL url = ResourceFindUtil.class.getClassLoader().getResource(path);
        if (url == null) {
            return fileNameList;
        }
        if ("file".equals(url.getProtocol())) {
            File file = new File(url.getFile());
            File[] child = file.listFiles();
            if (child == null) {
                return fileNameList;
            }
            for (File value : child) {
                if (value.isFile()) {
                    fileNameList.add(path+value.getName());
                } else {
                    fileNameList.addAll(getFileListByPath(path+value.getName()+"/"));
                }
            }
        } else if ("jar".equals(url.getProtocol())) {
            String jarPath = url.toString().substring(0, url.toString().indexOf("!/") + 2);
            URL jarURL = new URL(jarPath);
            JarURLConnection jarCon = (JarURLConnection) jarURL.openConnection();
            JarFile jarFile = jarCon.getJarFile();
            Enumeration<JarEntry> jarEntry = jarFile.entries();
            while (jarEntry.hasMoreElements()) {
                JarEntry entry = jarEntry.nextElement();
                String name = entry.getName();
                if (name.startsWith(path) && !entry.isDirectory()) {
                    fileNameList.add(name);
                }
            }
        }
        return fileNameList;
    }
}
