package cc.project.lnj.controller.admin;

import cc.project.lnj.domain.ApiOutPut;
import cc.project.lnj.domain.PermissionData;
import cc.project.lnj.domain.SysPermission;
import cc.project.lnj.service.PermissionService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequestMapping("/admin")
@Controller
public class PermissionController extends BaseController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("permission/index.html")
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        List<PermissionData> tree = permissionService.getPermissions();
        model.put("tree", tree);

        return "admin/permissionList";
    }

    @RequestMapping("permission/edit.html")
    public String edit(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        Boolean editParent = false;
        String checkboxStatus = "";

        SysPermission permission = new SysPermission();

        try {
            if (StringUtils.isNotBlank(request.getParameter("id")) && StringUtils.isNumeric(request.getParameter("id"))) {
                long id = Long.parseLong(request.getParameter("id"));
                permission = permissionService.selectByPrimaryKey(id);

                if (null != permission) {

                    if (permission.getParentid() == 0) {
                        editParent = true;
                    }
                    if (permission.getAvailable().equals("1")) {
                        checkboxStatus = "checked=\"checked\"";
                    }
                }
            }


            if (!editParent) {
                List<SysPermission> tree = permissionService.getPermissionByParentId(0l);
                model.put("tree", tree);
            }

            model.put("permission", permission);
            model.put("permissionType", permissionService.getPermissionEnums());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        model.put("checkboxStatus", checkboxStatus);
        return "admin/permissionEdit";
    }

    @RequestMapping("permission/add.html")
    public String add(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        Boolean rootPermission = true;

        try {
            if (StringUtils.isNotBlank(request.getParameter("id")) && StringUtils.isNumeric(request.getParameter("id"))) {
                long id = Long.parseLong(request.getParameter("id"));
                rootPermission = false;
                model.put("id", id);
            }


            if (!rootPermission) {
                List<SysPermission> tree = permissionService.getPermissionByParentId(0l);
                model.put("tree", tree);

            }

            model.put("permissionType", permissionService.getPermissionEnums());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return "admin/permissionAdd";
    }

    @RequestMapping("permission/save.html")
    public void save(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes) {

        ApiOutPut outPut = new ApiOutPut("0000");

        try {
            SysPermission sysPermission = new SysPermission();
            sysPermission.setName(request.getParameter("name"));
            sysPermission.setType(request.getParameter("type"));
            if (StringUtils.isNotBlank(request.getParameter("id"))) {
                sysPermission.setId(Long.valueOf(request.getParameter("id")));
            }
            if (StringUtils.isNotBlank(request.getParameter("parentid"))) {
                sysPermission.setParentid(Long.valueOf(request.getParameter("parentid")));
            } else {
                sysPermission.setParentid(0L);
            }

            sysPermission.setPercode(request.getParameter("percode"));
            sysPermission.setUrl(request.getParameter("url"));


            String available = request.getParameter("available");
            if (StringUtils.isNotBlank(available) && available.equals("on")) {
                sysPermission.setAvailable("1");
            } else {
                sysPermission.setAvailable("0");
            }

            SysPermission permission = permissionService.save(sysPermission);
            if (null != permission) {
                outPut.setMsg("数据添加成功");
            } else {
                outPut.setMsg("数据添加异常！");
                outPut.setStatusCode("5001");
            }
            String basePath = (String) request.getAttribute("basePath");
            outPut.setUrl(basePath + "admin/permission/index.html");

            request.setAttribute("outPut", outPut);

            request.getRequestDispatcher("/admin/alert.html").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("permission/update.html")
    public void update(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes) {

        ApiOutPut outPut = new ApiOutPut("0000");

        try {
            SysPermission sysPermission = new SysPermission();
            sysPermission.setName(request.getParameter("name"));
            sysPermission.setType(request.getParameter("type"));
            if (StringUtils.isNotBlank(request.getParameter("id"))) {
                sysPermission.setId(Long.valueOf(request.getParameter("id")));
            }
            if (StringUtils.isNotBlank(request.getParameter("parentid"))) {
                sysPermission.setParentid(Long.valueOf(request.getParameter("parentid")));
            }

            sysPermission.setPercode(request.getParameter("percode"));
            sysPermission.setUrl(request.getParameter("url"));


            String available = request.getParameter("available");
            if (StringUtils.isNotBlank(available) && available.equals("on")) {
                sysPermission.setAvailable("1");
            } else {
                sysPermission.setAvailable("0");
            }

            boolean updateResult = permissionService.updateByPrimaryKey(sysPermission);
            if (!updateResult) {
                outPut.setStatusCode("5001");
                outPut.setMsg("数据更新异常！");
            } else {
                outPut.setMsg("数据更新成功");
            }
            String basePath = (String) request.getAttribute("basePath");
            outPut.setUrl(basePath + "admin/permission/edit.html?id=" + request.getParameter("id"));

            request.setAttribute("outPut", outPut);

            request.getRequestDispatcher("/admin/alert.html").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("permission/delete.html")
    public void delete(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        ApiOutPut outPut = new ApiOutPut("0000");
        try {
            if (StringUtils.isNotBlank(request.getParameter("id")) && StringUtils.isNumeric(request.getParameter("id"))) {
                int delResult = permissionService.deletePermissionById(Long.valueOf(request.getParameter("id")));
                if(delResult > 0) {
                    outPut.setMsg("删除成功！");
                }else{
                    outPut.setMsg("操作出现异常！");
                }
            }
            String basePath = (String) request.getAttribute("basePath");
            outPut.setUrl(basePath + "admin/permission/index.html");
            request.setAttribute("outPut", outPut);
            request.getRequestDispatcher("/admin/alert.html").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
