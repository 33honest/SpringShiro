package cc.project.lnj.service.impl;

import cc.project.lnj.domain.PermissionData;
import cc.project.lnj.domain.SysPermission;
import cc.project.lnj.enums.PermissionEnum;
import cc.project.lnj.mapper.SysPermissionMapper;
import cc.project.lnj.service.PermissionService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PermissionServiceImpl implements PermissionService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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

    @Override
    public boolean updateByPrimaryKey(SysPermission sysPermission) {

        try {
            permissionMapper.updateByPrimaryKeySelective(sysPermission);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public SysPermission save(SysPermission sysPermission) {
        try {
            permissionMapper.insertSelective(sysPermission);
        } catch (Exception e) {
            logger.error("添加权限出现异常，参数：{}，异常：{}", sysPermission, e.getMessage());
            e.printStackTrace();
            return null;
        }
        return sysPermission;
    }

    @Override
    public int deletePermissionById(Long id) {

        try {
            return permissionMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除指定权限【" + id + "】失败，原因：{}", e.getMessage());
        }
        return 0;
    }

    @Override
    public List<SysPermission> getAvailablePermission(long parentId) {

        List<SysPermission> list = null;
        try {
            list = permissionMapper.getAvailablePermission(parentId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
