package cc.project.lnj.realm;

import cc.project.lnj.domain.SysPermission;
import cc.project.lnj.domain.SysRolePermission;
import cc.project.lnj.domain.SysUser;
import cc.project.lnj.domain.SysUserRole;
import cc.project.lnj.service.PermissionService;
import cc.project.lnj.service.RoleService;
import cc.project.lnj.service.UserRoleService;
import cc.project.lnj.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private PermissionService permissionService;


    //用于身份验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String userName = (String) token.getPrincipal();
        if (StringUtils.isBlank(userName)) {
            throw new UnknownAccountException("账户不可以为空");
        }

        try {
            SysUser sysUser = userService.getUserByUserCode(userName);
            if (null != sysUser) {
                return new SimpleAuthenticationInfo(userName, sysUser.getPassword(), this.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //用于授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        /*
        String userCode = (String) principals.getPrimaryPrincipal();
        List<String> permissions = new ArrayList<>();
        if (StringUtils.isNotBlank(userCode)) {
            SysUser sysUser = userService.getUserByUserCode(userCode);
            if (null != sysUser) {
                SysUserRole userRole = userRoleService.findByUserId(sysUser.getId());
                if (null != userRole) {
                    List<SysRolePermission> list = roleService.getListByRoleId(userRole.getSysRoleId());
                    for (SysRolePermission rolePermission : list) {
                        SysPermission permission = permissionService.selectByPrimaryKey(Long.valueOf(rolePermission.getSysPermissionId()));
                        permissions.add(permission.getPercode());
                    }

                }
                roleService.getRoleById(sysUser.getId());
            }
        }
        simpleAuthorizationInfo.addStringPermissions(permissions);
        */

        return simpleAuthorizationInfo;
    }
}
