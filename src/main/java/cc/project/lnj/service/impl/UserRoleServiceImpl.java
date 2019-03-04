package cc.project.lnj.service.impl;

import cc.project.lnj.domain.SysUserRole;
import cc.project.lnj.mapper.SysUserRoleMapper;
import cc.project.lnj.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Override
    public void insert(SysUserRole userRole) {
        try {
            userRoleMapper.insertSelective(userRole);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(SysUserRole userRole) {
        try {
            userRoleMapper.updateByUserId(userRole);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            userRoleMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public SysUserRole findByUserId(int userId) {
        SysUserRole userRole = null;
        try {
            userRole = userRoleMapper.selectByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userRole;
    }

    @Override
    public void deleteByUserId(int id) {
        try {
            userRoleMapper.deleteByUserId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
