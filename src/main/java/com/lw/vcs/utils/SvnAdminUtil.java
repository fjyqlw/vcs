package com.lw.vcs.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 练威
 * @version 创建时间：2018-01-07 10:51:16 <br/>
 * @类说明 SVN工具
 */

@Component
public class SvnAdminUtil {

    /**
     * Repositories目录中htpasswd文件的绝对路径
     */
    @Value("${vcs.htpasswd}")
    private String htpasswd;

    /**
     * 创建SVN用户
     *
     * @param userId
     * @param password
     * @return
     */
    public List<String> createUser(String userId, String password) {
        StringBuilder cmd = new StringBuilder("htpasswd.exe -b ");
        cmd.append(htpasswd);
        cmd.append(" ");
        cmd.append(userId);
        cmd.append(" ");
        cmd.append(password);

        return exec(cmd.toString());
    }

    /**
     * 删除SVN用户
     *
     * @param userId
     * @param password
     * @return
     */
    public List<String> deleteUser(String userId, String password) {
        StringBuilder cmd = new StringBuilder("htpasswd.exe -D ");
        cmd.append(htpasswd);
        cmd.append(" ");
        cmd.append(userId);
        cmd.append(" ");
        cmd.append(password);

        return exec(cmd.toString());
    }

    /**
     * 通用命令执行器
     *
     * @param cmd
     * @return 返回输出结果
     */
    public static List<String> exec(String cmd) {
        List<String> lineList = new ArrayList<String>();
        Process p = null;
        SequenceInputStream sis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {

            p = Runtime.getRuntime().exec(cmd);

            sis = new SequenceInputStream(p.getInputStream(), p.getErrorStream());
            isr = new InputStreamReader(sis, "utf-8");

            br = new BufferedReader(isr);

            String line = null;
            while ((line = br.readLine()) != null) {
                lineList.add(line);
                System.out.println(line);
            }

        } catch (IOException e) {
            // TODO: handle exception
        } finally {
            p.destroy();

            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                sis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return lineList;
    }
}
