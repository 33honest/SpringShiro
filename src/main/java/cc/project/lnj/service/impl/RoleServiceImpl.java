package cc.project.lnj.service.impl;

import cc.project.lnj.mapper.SysRoleMapper;
import cc.project.lnj.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    @Override
    public List<Object> getList() {

        try {
            List<Object> list = roleMapper.getList();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
