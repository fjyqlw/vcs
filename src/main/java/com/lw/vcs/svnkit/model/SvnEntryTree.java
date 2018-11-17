package com.lw.vcs.svnkit.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：lian.wei
 * @Date：2018/9/22 21:45
 * @Description：
 */
public class SvnEntryTree {
    private String fileName;
    private String title;
    private String author;
    private long revision;
    private String date;
    private String kind;
    private String url;
    private boolean expand = true;
    private List<SvnEntryTree> children = new ArrayList<>();

    private String id;
    private String label;

    public static SvnEntryTree create() {
        return new SvnEntryTree();
    }

    public String getAuthor() {
        return author;
    }

    public SvnEntryTree setAuthor(String author) {
        this.author = author;
        return this;
    }

    public long getRevision() {
        return revision;
    }

    public SvnEntryTree setRevision(long revision) {
        this.revision = revision;
        return this;
    }

    public String getDate() {
        return date;
    }

    public SvnEntryTree setDate(String date) {
        this.date = date;
        return this;
    }

    public String getKind() {
        return kind;
    }

    public SvnEntryTree setKind(String kind) {
        this.kind = kind;
        return this;
    }

    public List<SvnEntryTree> getChildren() {
        return children;
    }

    public SvnEntryTree setChildren(List<SvnEntryTree> children) {
        this.children = children;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public SvnEntryTree setFileName(String fileName) {
        this.fileName = fileName;
        this.title = fileName;
        this.label = fileName;
        return this;
    }

    public SvnEntryTree childrenAdd(SvnEntryTree svnEntryTree){
        this.children.add(svnEntryTree);
        return this;
    }

    public String getUrl() {
        return url;
    }

    public SvnEntryTree setUrl(String url) {
        this.url = url;
        this.id = url;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }
}
