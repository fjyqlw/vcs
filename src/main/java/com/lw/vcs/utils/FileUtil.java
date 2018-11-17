package com.lw.vcs.utils;

import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;

/**
 * @Author：lian.wei
 * @Date：2018/9/23 14:44
 * @Description：文件操作工具类
 */
public class FileUtil {

    /**
     * 递归删除文件夹和子文件
     * @param file
     */
    public static void deleteAll(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();

                for (File f : files) {
                    deleteAll(f);
                }
            }

            file.delete();
        }

    }

    /**
     * 文件复制
     * @param in
     * @param out
     * @throws IOException
     */
    public static void fileCopy(File in, File out) throws IOException {
        FileCopyUtils.copy(in,out);
    }
}
