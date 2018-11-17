package com.lw.vcs.utils;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.wc.ISVNDiffStatusHandler;
import org.tmatesoft.svn.core.wc.SVNDiffStatus;
import org.tmatesoft.svn.core.wc.SVNStatusType;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author：lian.wei
 * @Date：2018/9/22 18:54
 * @Description：
 */
public class ImplISVNDiffStatusHandler implements ISVNDiffStatusHandler {

    public Set<SVNDiffStatus> changes = new HashSet<>();

    public void handleDiffStatus(SVNDiffStatus status) {
        System.err.println(status.getModificationType());
        if (status.getModificationType() == SVNStatusType.STATUS_ADDED || status.getModificationType() == SVNStatusType.STATUS_MODIFIED) {
            changes.add(status);
        }
    }

    public Set<SVNDiffStatus> getChanges() {
        return changes;
    }
}