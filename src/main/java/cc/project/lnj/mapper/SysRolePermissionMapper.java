package cc.project.lnj.mapper;

import cc.project.lnj.domain.SysRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRolePermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRolePermission record);

    int insertSelective(SysRolePermission record);

    SysRolePermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRolePermission record);

    int updateByPrimaryKey(SysRolePermission record);

    int saveRolePermission(@Param("roleId") int roleId, @Param("permissionId") List<String> permissionId);

    int deleteByRoleId(int roleId);

    List<SysRolePermission> getListByRoleId(int roleId);
}