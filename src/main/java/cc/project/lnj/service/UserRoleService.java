package cc.project.lnj.service;

import cc.project.lnj.domain.SysUserRole;

public interface UserRoleService {

    void insert(SysUserRole userRole);

    void update(SysUserRole userRole);

    void delete(int id);

    void deleteByUserId(int id);

    SysUserRole findByUserId(int userId);

}
