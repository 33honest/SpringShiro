package cc.project.lnj.domain;

import java.util.List;

public class PermissionData extends SysPermission {


    private List<SysPermission> list;

    public List<SysPermission> getList() {
        return list;
    }

    public void setList(List<SysPermission> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PermissionData{" +
                "list=" + list +
                '}';
    }
}
