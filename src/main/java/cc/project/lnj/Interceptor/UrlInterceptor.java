package cc.project.lnj.Interceptor;

import cc.project.lnj.domain.*;
import cc.project.lnj.service.PermissionService;
import cc.project.lnj.service.RoleService;
import cc.project.lnj.service.UserRoleService;
import cc.project.lnj.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UrlInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private PermissionService permissionService;

    private static final String[] filterUrl = new String[] {
            "/login.html",
            "/logout.html",
            "/alert.html",
            "/index.html",
            "/error.html"
    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();
        if(StringUtils.isNotBlank(uri)) {
            uri = uri.replace(";","");
        }

        System.out.println("当前URI：" + uri);
        String userName = request.getRemoteUser();
        List<String> permissions = new ArrayList<>();
        if (StringUtils.isNotBlank(userName)) {
            SysUser sysUser = userService.getUserByUserCode(userName);
            if (null != sysUser) {
                SysUserRole userRole = userRoleService.findByUserId(sysUser.getId());
                if (null != userRole) {
                    List<SysRolePermission> list = roleService.getListByRoleId(userRole.getSysRoleId());
                    for (SysRolePermission rolePermission : list) {
                        SysPermission permission = permissionService.selectByPrimaryKey(Long.valueOf(rolePermission.getSysPermissionId()));
                        permissions.add(permission.getUrl());
                    }
                }

            }
        }

        if (StringUtils.isNotBlank(uri) && uri.endsWith(".html")) {
            if(uri.indexOf("admin") > 0) {
                uri = uri.substring(uri.indexOf("admin") + 5);
            }


            for (int i = 0; i < filterUrl.length; i++) {
                if(uri.equals(filterUrl[i])) {
                    return true;
                }
                
            }
            
            if (null != permissions && permissions.size() > 0) {
                if (permissions.contains(uri)) {
                    return true;
                }
            }

            ApiOutPut outPut = new ApiOutPut("5001");
            String basePath = (String) request.getAttribute("basePath");
            outPut.setUrl(basePath + "admin/index.html");
            request.setAttribute("outPut", outPut);
            try {
                outPut.setMsg("权限不足，请联系管理员开通此权限");
                request.getRequestDispatcher("/admin/alert.html").forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            return true;
        }


        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
