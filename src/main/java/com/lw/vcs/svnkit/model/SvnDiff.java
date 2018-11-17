package com.lw.vcs.svnkit.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author：lian.wei
 * @Date：2018/9/22 16:17
 * @Description：
 */
public class SvnDiff {
    private List<File> fileList = new ArrayList<>();
    private String message;

    public static SvnDiff create() {
        return new SvnDiff();
    }

    public List<File> getFileList() {
        return fileList;
    }

    public SvnDiff setFileList(List<File> fileList) {
        this.fileList = fileList;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public SvnDiff setMessage(String message) {
        this.message = message;
        return this;
    }
    public SvnDiff addFile(File file){
        this.fileList.add(file);
        return this;
    }
}
