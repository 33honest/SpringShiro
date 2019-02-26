package cc.project.lnj.mapper;

import cc.project.lnj.domain.sysPermission;

public interface sysPermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(sysPermission record);

    int insertSelective(sysPermission record);

    sysPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(sysPermission record);

    int updateByPrimaryKey(sysPermission record);
}