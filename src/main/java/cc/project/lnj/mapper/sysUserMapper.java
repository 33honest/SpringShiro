package cc.project.lnj.mapper;

import cc.project.lnj.domain.sysUser;

public interface sysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(sysUser record);

    int insertSelective(sysUser record);

    sysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(sysUser record);

    int updateByPrimaryKey(sysUser record);
}