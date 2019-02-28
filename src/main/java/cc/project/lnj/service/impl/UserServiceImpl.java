package cc.project.lnj.service.impl;

import cc.project.lnj.domain.SysUser;
import cc.project.lnj.mapper.SysUserMapper;
import cc.project.lnj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public List<SysUser> getList() {

        List<SysUser> list = new ArrayList<>();

        try {
            list = userMapper.getList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
