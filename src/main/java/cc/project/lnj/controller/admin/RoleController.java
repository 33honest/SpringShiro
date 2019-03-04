package cc.project.lnj.controller.admin;

import cc.project.lnj.domain.*;
import cc.project.lnj.service.PermissionService;
import cc.project.lnj.service.RoleService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("role/index.html")
    public String userList(HttpServletRequest req, HttpServletResponse rep, ModelMap map) {


        List<SysRole> list = roleService.getList();
        map.put("list", list);

        return "admin/roleList";
    }

    @RequestMapping("role/add.html")
    public String add(HttpServletRequest req, HttpServletResponse rep, ModelMap map) {


        return "admin/roleAdd";
    }

    @RequestMapping("role/save.html")
    public void save(HttpServletRequest req, HttpServletResponse rep, ModelMap map) {

        ApiOutPut outPut = new ApiOutPut("0000");
        SysRole role = new SysRole();
        String name = req.getParameter("name");
        String available = req.getParameter("available");
        if (StringUtils.isNotBlank(name)) {
            role.setName(name);
            if (StringUtils.isNotBlank(available) && available.equals("on")) {
                role.setAvailable("1");
            } else {
                role.setAvailable("0");
            }
            try {
                roleService.saveRole(role);
                outPut.setMsg("角色【" + name + "】添加成功！");
            } catch (Exception e) {
                outPut.setStatusCode("5010");
                e.printStackTrace();
                outPut.setMsg("角色【" + name + "】添加出现异常，原因：" + e.getMessage());
            }
        } else {
            outPut.setStatusCode("5011");
            outPut.setMsg("角色名称不可以为空");
        }

        String basePath = (String) req.getAttribute("basePath");
        outPut.setUrl(basePath + "admin/role/index.html");

        req.setAttribute("outPut", outPut);

        try {
            req.getRequestDispatcher("/admin/alert.html").forward(req, rep);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("role/edit.html")
    public String edit(HttpServletRequest req, HttpServletResponse rep, ModelMap map) {

        if (StringUtils.isNotBlank(req.getParameter("id")) && StringUtils.isNumeric(req.getParameter("id"))) {

            SysRole role = roleService.getRoleById(Integer.valueOf(req.getParameter("id")));
            if (null != role) {
                String roleChecked = "";
                if (role.getAvailable().equals("1")) {
                    roleChecked = "checked='checked'";
                }
                map.put("role", role);
                map.put("roleChecked", roleChecked);
            }

        } else {
            try {
                ApiOutPut outPut = new ApiOutPut("5010");
                outPut.setMsg("参数传递不正确");
                req.setAttribute("outPut", outPut);
                req.getRequestDispatcher("/admin/alert.html").forward(req, rep);
                return null;
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "admin/roleEdit";
    }

    @RequestMapping("role/update.html")
    public void update(HttpServletRequest req, HttpServletResponse rep, ModelMap map) {

        ApiOutPut outPut = new ApiOutPut("0000");
        SysRole role = new SysRole();
        String name = req.getParameter("name");
        String available = req.getParameter("available");
        if (StringUtils.isNotBlank(name)) {
            role.setName(name);
            if (StringUtils.isNotBlank(available) && available.equals("on")) {
                role.setAvailable("1");
            } else {
                role.setAvailable("0");
            }
            role.setId(Integer.valueOf(req.getParameter("id")));
            try {
                roleService.updateRole(role);
                outPut.setMsg("角色【" + name + "】更新成功！");
            } catch (Exception e) {
                outPut.setStatusCode("5010");
                e.printStackTrace();
                outPut.setMsg("角色【" + name + "】更新出现异常，原因：" + e.getMessage());
            }
        } else {
            outPut.setStatusCode("5011");
            outPut.setMsg("角色名称不可以为空");
        }

        String basePath = (String) req.getAttribute("basePath");
        outPut.setUrl(basePath + "admin/role/index.html");

        req.setAttribute("outPut", outPut);

        try {
            req.getRequestDispatcher("/admin/alert.html").forward(req, rep);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("role/delete.html")
    public void delete(HttpServletRequest req, HttpServletResponse rep, ModelMap map) {

        ApiOutPut outPut = new ApiOutPut("0000");
        try {
            if (StringUtils.isNotBlank(req.getParameter("id")) && StringUtils.isNumeric(req.getParameter("id"))) {
                SysRole role = roleService.delById(Integer.valueOf(req.getParameter("id")));
                if (null != role) {
                    outPut.setMsg("角色【" + role.getName() + "】删除成功！");
                } else {
                    outPut.setMsg("角色不存在");
                }

            } else {
                outPut.setMsg("参数传递异常！");
                outPut.setStatusCode("5010");
            }

            String basePath = (String) req.getAttribute("basePath");
            outPut.setUrl(basePath + "admin/role/index.html");
            req.setAttribute("outPut", outPut);
            req.getRequestDispatcher("/admin/alert.html").forward(req, rep);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @RequestMapping("role/permission.html")
    public String editPermission(HttpServletRequest req, HttpServletResponse rep, ModelMap map) {

        if(StringUtils.isNotBlank(req.getParameter("id")) && StringUtils.isNumeric(req.getParameter("id"))) {
            List<PermissionData> list = new ArrayList<>();
            List<SysPermission> rootPermission = permissionService.getAvailablePermission(0L);
            for (SysPermission p : rootPermission) {
                PermissionData pData = new PermissionData();
                List<SysPermission> subList = permissionService.getAvailablePermission(p.getId());
                BeanUtils.copyProperties(p, pData);
                pData.setList(subList);
                list.add(pData);
            }
            map.put("roleId", req.getParameter("id"));
            map.put("list", list);
        }else{
            ApiOutPut outPut = new ApiOutPut("5001");
            String basePath = (String) req.getAttribute("basePath");
            outPut.setUrl(basePath + "admin/role/index.html");
            req.setAttribute("outPut", outPut);
            try {
                outPut.setMsg("参数传递有误！");
                req.getRequestDispatcher("/admin/alert.html").forward(req, rep);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "admin/rolePermission";
    }

    @RequestMapping("role/permission_update.html")
    public void permissionUpdate(HttpServletRequest req, HttpServletResponse rep) {

        ApiOutPut outPut = new ApiOutPut("0000");
        if(StringUtils.isNotBlank(req.getParameter("roleId")) && StringUtils.isNumeric(req.getParameter("roleId"))) {

            String roleId = req.getParameter("roleId");
            String[] permissionId = req.getParameterValues("permissionId");

            System.out.println(permissionId);
        }else{

            outPut.setStatusCode("5001");
            outPut.setMsg("参数传递有误！");

        }
        try {

            String basePath = (String) req.getAttribute("basePath");
            outPut.setUrl(basePath + "admin/role/index.html");
            req.setAttribute("outPut", outPut);
            req.getRequestDispatcher("/admin/alert.html").forward(req, rep);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
