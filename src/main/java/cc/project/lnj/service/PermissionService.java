package cc.project.lnj.service;

import cc.project.lnj.domain.PermissionData;
import cc.project.lnj.domain.SysPermission;

import java.util.List;
import java.util.Map;

public interface PermissionService {

    public List<SysPermission> getRootPermission();

    public List<SysPermission> getPermissionByParentId(long parentId);

    public List<PermissionData> getPermissions();

    public List<Map<String, String>> getPermissionEnums();

    public SysPermission selectByPrimaryKey(Long id);

    public boolean updateByPrimaryKey(SysPermission sysPermission);

}
