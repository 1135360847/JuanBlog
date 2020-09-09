package com.zzw.core.converter;

import com.zzw.core.api.po.Permission;
import com.zzw.core.api.vo.PermissionTree;

import java.util.ArrayList;
import java.util.List;

/**
 * create by zhangzhiwen on 2020/7/23
 */
public class PermissionTreeUtils {

    /**
     * 使用递归方法建菜单
     * @param treeNodes
     * @return
     */
    public static List<PermissionTree> build(List<Permission> treeNodes) {
        List<PermissionTree> trees = new ArrayList<>();
        for (Permission treeNode : treeNodes) {
            if ("0".equals(treeNode.getPid())) {
                trees.add(findChildren(treeNode,treeNodes));
            }
        }
        return trees;
    }


    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    public static PermissionTree findChildren(Permission treeNode,List<Permission> treeNodes) {
        PermissionTree permissionTree = new PermissionTree();
        permissionTree.setChildren(new ArrayList<PermissionTree>());
        permissionTree.setId(treeNode.getId());
        permissionTree.setName(treeNode.getName());
        for (Permission it : treeNodes) {
            if(treeNode.getId().equals(it.getPid())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                permissionTree.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return permissionTree;
    }

}
