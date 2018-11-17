package com.lw.vcs.utils;

import com.lw.vcs.svnkit.model.SvnEntryTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @Author：lian.wei
 * @Date：2018/9/22 21:27
 * @Description：
 */
@Component
public class SvnDirectoryUtil {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private SvnAuthUtil svnAuthUtil;

    /**
     * 获取SVN目录树
     *
     * @param svnUrl
     * @return
     * @throws SVNException
     */
    public SvnEntryTree showTree(String svnUrl) throws SVNException {
        SvnEntryTree svnEntryTree = SvnEntryTree.create().setUrl(svnUrl);
        //打印版本库的目录树结构
        listEntries(svnUrl, "", svnEntryTree);

        return svnEntryTree;
    }


    /**
     * 获取SVN目录列表
     *
     * @param svnUrl
     * @return
     * @throws SVNException
     */
    public List<SvnEntryTree> showList(String svnUrl) throws SVNException {

        //打印版本库的目录树结构
        return listEntries(svnUrl, "");

    }

     /*

     * 此函数递归的获取版本库中某一目录下的所有条目。

     */

    public void listEntries(String svnUrl, String path, SvnEntryTree parentSvnEntryTree) throws SVNException {

        //获取版本库的path目录下的所有条目。参数－1表示是最新版本。

        Collection entries = svnAuthUtil.getSVNRepository(svnUrl).getDir(path, -1, null, (Collection) null);
        Iterator iterator = entries.iterator();

        while (iterator.hasNext()) {

            SVNDirEntry entry = (SVNDirEntry) iterator.next();
            System.out.println("/" + (path.equals("") ? "" : path + "/") + entry.getName() + " (author: '" + entry.getAuthor() + "'; revision: " + entry.getRevision() + "; date: " + entry.getDate() + ")");

            SvnEntryTree svnEntryTree = SvnEntryTree.create()
                    .setAuthor(entry.getAuthor())
                    .setRevision(entry.getRevision())
                    .setFileName(entry.getName())
                    .setKind(entry.getKind().toString())
                    .setDate(sdf.format(entry.getDate()))
                    .setUrl(entry.getURL().toString());

            parentSvnEntryTree.childrenAdd(svnEntryTree);

            /*
             * 检查此条目是否为目录，如果为目录递归执行

             */
            if (entry.getKind() == SVNNodeKind.DIR) {
                listEntries(svnUrl, (path.equals("")) ? entry.getName() : path + "/" + entry.getName(), svnEntryTree);
            }


        }

    }

     /*

     * 此函数递归的获取版本库中某一目录下的所有条目。

     */

    public List<SvnEntryTree> listEntries(String svnUrl, String path) throws SVNException {
        List<SvnEntryTree> list = new ArrayList<>();

        //获取版本库的path目录下的所有条目。参数－1表示是最新版本。
        Collection entries = svnAuthUtil.getSVNRepository(svnUrl).getDir(path, -1, null, (Collection) null);
        Iterator iterator = entries.iterator();

        while (iterator.hasNext()) {

            SVNDirEntry entry = (SVNDirEntry) iterator.next();
            System.out.println("/" + (path.equals("") ? "" : path + "/") + entry.getName() + " (author: '" + entry.getAuthor() + "'; revision: " + entry.getRevision() + "; date: " + entry.getDate() + ")");

            SvnEntryTree svnEntryTree = SvnEntryTree.create()
                    .setAuthor(entry.getAuthor())
                    .setRevision(entry.getRevision())
                    .setFileName(entry.getName())
                    .setKind(entry.getKind().toString())
                    .setDate(sdf.format(entry.getDate()))
                    .setUrl(entry.getURL().toString());

            list.add(svnEntryTree);
        }

        return list;
    }
}


