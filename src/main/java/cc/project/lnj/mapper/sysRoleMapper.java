package cc.project.lnj.mapper;

import cc.project.lnj.domain.sysRole;

public interface sysRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(sysRole record);

    int insertSelective(sysRole record);

    sysRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(sysRole record);

    int updateByPrimaryKey(sysRole record);
}