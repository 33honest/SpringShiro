package cc.project.lnj.service;

import cc.project.lnj.domain.SysRole;

import java.util.List;

public interface RoleService {

    public List<SysRole> getList();

    public int saveRole(SysRole role);

    public int updateRole(SysRole role);

    public SysRole getRoleById(int id);

    public SysRole delById(int id);

}
