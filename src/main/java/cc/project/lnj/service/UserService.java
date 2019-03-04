package cc.project.lnj.service;

import cc.project.lnj.domain.SysUser;
import cc.project.lnj.domain.SysUserExtends;

import java.util.List;

public interface UserService {

    public List<SysUserExtends> getList();

    public int save(SysUser user);

    public int update(SysUser user);

    public int delete(int id);

    public SysUserExtends getUserAndRole(int id);

}
