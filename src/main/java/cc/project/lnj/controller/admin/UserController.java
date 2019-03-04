package cc.project.lnj.controller.admin;

import cc.project.lnj.domain.*;
import cc.project.lnj.service.RoleService;
import cc.project.lnj.service.UserRoleService;
import cc.project.lnj.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    @Autowired
    private UserRoleService userRoleService;

    @RequestMapping("userList.html")
    public String userList(HttpServletRequest req, HttpServletResponse rep, ModelMap map) {

        List<SysUserExtends> list = userService.getList();
        System.out.println(list);
        map.put("list", list);

        return "admin/userList";
    }

    @RequestMapping("user/add.html")
    public String add(HttpServletRequest req, HttpServletResponse rep, ModelMap map) {

        List<SysRole> list = roleService.getList();
        map.put("roleTree", list);

        return "admin/userAdd";
    }

    @RequestMapping("user/edit.html")
    public String edit(HttpServletRequest req, HttpServletResponse rep, ModelMap map) {

        ApiOutPut outPut = new ApiOutPut("5002");
        if (StringUtils.isNotBlank(req.getParameter("id")) && StringUtils.isNumeric(req.getParameter("id"))) {
            List<SysRole> list = roleService.getList();
            map.put("roleTree", list);

            SysUserExtends userExtends = userService.getUserAndRole(Integer.valueOf(req.getParameter("id")));
            if (null != userExtends) {
                String lockChecked = "";
                if (userExtends.getLocked().equals("0")) {
                    lockChecked = "checked=\"checked\"";
                }
                map.put("lockChecked", lockChecked);
                map.put("user", userExtends);
            }

        } else {
            outPut.setMsg("参数传递异常！");
            String basePath = (String) req.getAttribute("basePath");
            outPut.setUrl(basePath + "admin/userList.html");
            req.setAttribute("outPut", outPut);

            try {
                req.getRequestDispatcher("/admin/alert.html").forward(req, rep);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return "admin/userEdit";
    }

    @RequestMapping("user/save.html")
    public void save(HttpServletRequest req, HttpServletResponse rep, ModelMap map) {

        ApiOutPut outPut = new ApiOutPut("0000");
        SysUser user = new SysUser();
        String username = req.getParameter("username");
        String locked = req.getParameter("locked");
        String pwd = req.getParameter("password");
        String usercode = req.getParameter("usercode");
        String type = req.getParameter("type");
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(pwd) && StringUtils.isNotBlank(usercode)) {
            user.setUsername(username);
            user.setPassword(pwd);
            user.setUsercode(usercode);


            if (StringUtils.isNotBlank(locked) && locked.equals("on")) {
                user.setLocked("0");
            } else {
                user.setLocked("1");
            }

            try {
                int userId = userService.save(user);
                if (userId > 0) {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setSysRoleId(Integer.valueOf(req.getParameter("type")));
                    userRole.setSysUserId(userId);
                    userRoleService.insert(userRole);
                    outPut.setMsg("用户【" + username + "】添加成功！");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }


        } else {
            outPut.setStatusCode("5011");
            outPut.setMsg("请完善表单资料！");
        }

        String basePath = (String) req.getAttribute("basePath");
        outPut.setUrl(basePath + "admin/userList.html");

        req.setAttribute("outPut", outPut);

        try {
            req.getRequestDispatcher("/admin/alert.html").forward(req, rep);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("user/update.html")
    public void update(HttpServletRequest req, HttpServletResponse rep, ModelMap map) {

        ApiOutPut outPut = new ApiOutPut("0000");
        SysUser user = new SysUser();
        String id = req.getParameter("id");
        String username = req.getParameter("username");
        String locked = req.getParameter("locked");
        String pwd = req.getParameter("password");
        String usercode = req.getParameter("usercode");
        String type = req.getParameter("type");
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(usercode) && StringUtils.isNotBlank(id)) {
            user.setUsername(username);
            user.setUsercode(usercode);
            user.setId(Integer.valueOf(id));

            if (StringUtils.isNotBlank(pwd)) {
                user.setPassword(pwd);
            }

            if (StringUtils.isNotBlank(locked) && locked.equals("on")) {
                user.setLocked("0");
            } else {
                user.setLocked("1");
            }

            try {
                int userId = userService.update(user);
                if (userId > 0) {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setSysRoleId(Integer.valueOf(req.getParameter("type")));
                    userRole.setSysUserId(user.getId());
                    userRoleService.update(userRole);
                    outPut.setMsg("用户【" + username + "】修改成功！");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }


        } else {
            outPut.setStatusCode("5011");
            outPut.setMsg("请完善表单资料！");
        }

        String basePath = (String) req.getAttribute("basePath");
        outPut.setUrl(basePath + "admin/userList.html");

        req.setAttribute("outPut", outPut);

        try {
            req.getRequestDispatcher("/admin/alert.html").forward(req, rep);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("user/delete.html")
    public void delete(HttpServletRequest req, HttpServletResponse rep, ModelMap map) {

        ApiOutPut outPut = new ApiOutPut("0000");
        if (StringUtils.isNotBlank(req.getParameter("id")) && StringUtils.isNumeric(req.getParameter("id"))) {
            try {
                String id = req.getParameter("id");
                int delRst = userService.delete(Integer.valueOf(id));
                if (delRst > 0) {
                    userRoleService.deleteByUserId(Integer.valueOf(id));
                }
                outPut.setMsg("用户信息删除成功！");
            } catch (NumberFormatException e) {
                outPut.setStatusCode("5010");
                outPut.setMsg("删除用户出现异常，异常原因:{}" + e.getMessage());
                e.printStackTrace();
            }
        }

        String basePath = (String) req.getAttribute("basePath");
        outPut.setUrl(basePath + "admin/userList.html");

        req.setAttribute("outPut", outPut);

        try {
            req.getRequestDispatcher("/admin/alert.html").forward(req, rep);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
