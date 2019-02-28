package cc.project.lnj.service.impl;

import cc.project.lnj.domain.PermissionData;
import cc.project.lnj.domain.SysPermission;
import cc.project.lnj.enums.PermissionEnum;
import cc.project.lnj.mapper.SysPermissionMapper;
import cc.project.lnj.service.PermissionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private SysPermissionMapper permissionMapper;

    @Override
    public List<SysPermission> getRootPermission() {
        return null;
    }


    @Override
    public List<SysPermission> getPermissionByParentId(long parentId) {

        List<SysPermission> resultList = new ArrayList<>();
        try {
            List<SysPermission> list = permissionMapper.getPermissionByParentId(parentId);
            for(SysPermission obj : list) {
                if(StringUtils.isNotBlank(obj.getType())) {
                    obj.setType(PermissionEnum.getPermission(obj.getType()).getName());
                }
                resultList.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return resultList;
    }

    /**
     * 获取权限配置（二级）目录
     * @return
     */
    @Override
    public List<PermissionData> getPermissions() {
        List<PermissionData> resultList = new ArrayList<>();

        List<SysPermission> rootList = getPermissionByParentId(0l);
        if(null != rootList && rootList.size() > 0) {

            for (SysPermission root: rootList) {
                PermissionData permissionData = new PermissionData();
                List<SysPermission> subList = getPermissionByParentId(root.getId());
                BeanUtils.copyProperties(root, permissionData);
                permissionData.setList(subList);
                resultList.add(permissionData);
            }

        }
        return resultList;
    }

    @Override
    public List<Map<String, String>> getPermissionEnums() {

        List<Map<String, String>> enums = new ArrayList<>();

        for (PermissionEnum obj: PermissionEnum.values()) {
            Map<String, String> map = new HashMap<>();
            map.put("code", obj.getCode());
            map.put("name", obj.getName());
            enums.add(map);
        }

        return enums;
    }

    @Override
    public SysPermission selectByPrimaryKey(Long id) {

        SysPermission permission = null;

        try {
            permission = permissionMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return permission;
    }
}
