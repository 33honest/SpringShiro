package cc.project.lnj.mapper;

import cc.project.lnj.domain.SysUser;
import cc.project.lnj.domain.SysUserExtends;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    List<SysUser> getList();

    List<SysUserExtends> geUsertListInRole();

    SysUserExtends geUserAndRole(Integer id);
}