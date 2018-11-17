package com.lw.vcs.utils;

import org.tmatesoft.svn.core.wc.ISVNChangelistHandler;
import org.tmatesoft.svn.core.wc.SVNDiffStatus;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author：lian.wei
 * @Date：2018/9/22 19:47
 * @Description：
 */
public class ImplISVNChangelistHandler implements ISVNChangelistHandler {

    public Set<SVNDiffStatus> changes = new HashSet<>();

    @Override
    public void handle(File file, String s) {
        System.out.println(s);
    }

    public Set<SVNDiffStatus> getChanges() {
        return changes;
    }
}
