package cc.project.lnj.service.impl;

import cc.project.lnj.domain.SysRole;
import cc.project.lnj.mapper.SysRoleMapper;
import cc.project.lnj.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysRoleMapper roleMapper;

    @Override
    public List<SysRole> getList() {

        try {
            List<SysRole> list = roleMapper.getList();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public int saveRole(SysRole role) {

        try {
            int rtnId = roleMapper.insertSelective(role);
            System.out.println("返回值：" + rtnId);
            logger.debug("角色添加成功，参数 ：{}", role);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("角色添加出现异常，参数：{}，异常信息：{}", role, e.getMessage());
        }

        return 0;
    }

    @Override
    public int updateRole(SysRole role) {

        try {
            int updateRst = roleMapper.updateByPrimaryKeySelective(role);
            return updateRst;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("更新角色，参数：{}，异常信息：{}", role, e.getMessage());
        }

        return 0;
    }

    @Override
    public SysRole getRoleById(int id) {

        SysRole role = null;
        try {
            role = roleMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            logger.error("角色不存在");
            e.printStackTrace();
        }

        return role;
    }


    @Override
    public SysRole delById(int id) {

        SysRole role = new SysRole();

        try {
            role = roleMapper.selectByPrimaryKey(id);
            if (null != role) {
                roleMapper.deleteByPrimaryKey(id);
            }
        } catch (Exception e) {
            logger.error("角色删除存在异常，异常信息：{}", e.getMessage());
            e.printStackTrace();
        }

        return role;
    }
}
