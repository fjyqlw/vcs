package com.lw.vcs.utils;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Author：lian.wei
 * @Date：2018/10/12 21:25
 * @Description：
 */
public class ResourceUtil {

    public static String read(String path) {
        Resource resource = new ClassPathResource(path);
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try {

            inputStream = resource.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String b;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            while ((b = bufferedReader.readLine()) != null) {
                stringBuffer.append(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        System.out.println(stringBuffer.toString());

        return stringBuffer.toString();
    }
}
