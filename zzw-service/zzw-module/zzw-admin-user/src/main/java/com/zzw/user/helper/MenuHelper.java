package com.zzw.user.helper;

import com.alibaba.fastjson.JSONObject;
import com.zzw.core.api.po.Permission;
import com.zzw.core.api.vo.Mate;
import com.zzw.core.api.vo.MenuTree;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 根据权限数据构建登录用户左侧菜单数据
 * </p>
 * @since 2019-11-11
 */
public class MenuHelper {

    /**
     * 构建菜单
     * @param treeNodes
     * @return
     */
    public static List<MenuTree> build(List<Permission> treeNodes) {
        List<MenuTree> menuTrees = new ArrayList<>();
        if(treeNodes.size() == 1) {
            Permission topNode = treeNodes.get(0);
            //左侧一级菜单
            List<Permission> oneMenuList = topNode.getChildren();
            for(Permission one :oneMenuList) {
                MenuTree oneMenu = new MenuTree();
                oneMenu.setPath(one.getPath());
                oneMenu.setComponent(one.getComponent());
                oneMenu.setRedirect("noredirect");
                oneMenu.setName("name_"+one.getId());
                oneMenu.setHidden(false);
                Mate oneMeta = new Mate();
                oneMeta.setTitle(one.getName());
                oneMeta.setIcon(one.getIcon());
                oneMenu.setMeta(oneMeta);
                
                List<MenuTree> children = new ArrayList<>();
                List<Permission> twoMenuList = one.getChildren();
                for(Permission two :twoMenuList) {
                    MenuTree twoMenu = new MenuTree();
                    twoMenu.setPath(two.getPath());
                    twoMenu.setComponent(two.getComponent());
                    twoMenu.setName("name_"+two.getId());
                    twoMenu.setHidden(false);
                    Mate twoMeta = new Mate();
                    twoMeta.setTitle(two.getName());
                    twoMeta.setIcon(two.getIcon());
                    twoMenu.setMeta(twoMeta);
                    children.add(twoMenu);

                    List<Permission> threeMenuList = two.getChildren();
                    for(Permission three :threeMenuList) {
                        if(StringUtils.isEmpty(three.getPath())) continue;

                        MenuTree threeMenu = new MenuTree();
                        threeMenu.setPath(three.getPath());
                        threeMenu.setComponent(three.getComponent());
                        threeMenu.setName("name_"+three.getId());
                        threeMenu.setHidden(true);

                        Mate threeMeta = new Mate();
                        threeMeta.setTitle(three.getName());
                        threeMenu.setMeta(threeMeta);

                        children.add(threeMenu);
                    }
                }
                oneMenu.setChildren(children);
                menuTrees.add(oneMenu);
            }
        }
        return menuTrees;
    }
}
