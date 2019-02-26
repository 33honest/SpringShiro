package cc.project.lnj.mapper;

import cc.project.lnj.domain.sysUserRole;

public interface sysUserRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(sysUserRole record);

    int insertSelective(sysUserRole record);

    sysUserRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(sysUserRole record);

    int updateByPrimaryKey(sysUserRole record);
}