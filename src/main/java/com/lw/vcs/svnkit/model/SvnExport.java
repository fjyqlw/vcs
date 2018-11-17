package com.lw.vcs.svnkit.model;

import java.io.ByteArrayOutputStream;

/**
 * @Author：lian.wei
 * @Date：2018/9/22 23:25
 * @Description：
 */
public class SvnExport {
    private ByteArrayOutputStream bos;
    private String fileName;

    public static SvnExport create(){
        return new SvnExport();
    }

    public ByteArrayOutputStream getBos() {
        return bos;
    }

    public SvnExport setBos(ByteArrayOutputStream bos) {
        this.bos = bos;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public SvnExport setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }
}
