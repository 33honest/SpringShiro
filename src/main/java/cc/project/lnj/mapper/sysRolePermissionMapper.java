package cc.project.lnj.mapper;

import cc.project.lnj.domain.sysRolePermission;

public interface sysRolePermissionMapper {
    int deleteByPrimaryKey(String id);

    int insert(sysRolePermission record);

    int insertSelective(sysRolePermission record);

    sysRolePermission selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(sysRolePermission record);

    int updateByPrimaryKey(sysRolePermission record);
}