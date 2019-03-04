package cc.project.lnj.service;

import cc.project.lnj.domain.SysRole;
import cc.project.lnj.domain.SysRolePermission;

import java.util.List;

public interface RoleService {

    public List<SysRole> getList();

    public int saveRole(SysRole role);

    public int updateRole(SysRole role);

    public SysRole getRoleById(int id);

    public SysRole delById(int id);

    public int saveRolePermission(int roleId, List<String> permissionId);

    public List<SysRolePermission> getListByRoleId(int roleId);

}
