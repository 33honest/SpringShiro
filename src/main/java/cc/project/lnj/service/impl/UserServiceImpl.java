package cc.project.lnj.service.impl;

import cc.project.lnj.domain.SysUser;
import cc.project.lnj.domain.SysUserExtends;
import cc.project.lnj.domain.SysUserRole;
import cc.project.lnj.mapper.SysUserMapper;
import cc.project.lnj.service.UserRoleService;
import cc.project.lnj.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private UserRoleService userRoleService;


    @Override
    public List<SysUserExtends> getList() {

        List<SysUserExtends> list = new ArrayList<>();

        try {
            list = userMapper.geUsertListInRole();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    @Override
    public int save(SysUser user) {

        try {

            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            int saveRst = userMapper.insertSelective(user);
            if (saveRst > 0 && user.getId() > 0) {
                logger.info("用户资料添加成功！");

                return user.getId();
            } else {
                logger.debug("用户资料添加失败！！");
            }
        } catch (Exception e) {
            logger.error("添加用户出现异常，参数：{}，异常信息：{}", user, e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int update(SysUser user) {

        try {
            if (!StringUtils.isEmpty(user.getPassword())) {
                user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            }

            return userMapper.updateByPrimaryKeySelective(user);
        } catch (Exception e) {
            logger.error("更新用户信息出现异常：{}", e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int delete(int id) {

        if (id > 0) {
            try {
                int delRst = userMapper.deleteByPrimaryKey(id);
                if (delRst > 0) {
                    SysUserRole userRole = userRoleService.findByUserId(id);
                    if (null != userRole) {
                        userRoleService.delete(userRole.getId());
                    }
                }
                return 1;
            } catch (Exception e) {
                logger.error("删除用户出现异常，异常信息：{}", e.getMessage());
                e.printStackTrace();
            }
        }

        return 0;
    }

    @Override
    public SysUserExtends getUserAndRole(int id) {
        SysUserExtends user = null;

        try {
            user = userMapper.geUserAndRole(id);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public SysUser getUserByUserCode(String userCode) {

        SysUser user = null;
        try {
            user = userMapper.getUserByUserCode(userCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
