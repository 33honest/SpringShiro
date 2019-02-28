package cc.project.lnj.controller.admin;

import cc.project.lnj.domain.PermissionData;
import cc.project.lnj.domain.SysPermission;
import cc.project.lnj.enums.PermissionEnum;
import cc.project.lnj.service.PermissionService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        SysPermission permission = new SysPermission();

        try {
            if (StringUtils.isNotBlank(request.getParameter("id")) && StringUtils.isNumeric(request.getParameter("id"))) {
                long id = Long.parseLong(request.getParameter("id"));
                permission = permissionService.selectByPrimaryKey(id);

                if(null != permission) {

                    if(permission.getParentid() == 0) {
                        editParent = true;
                    }
                }
            }


            if(!editParent) {
                List<SysPermission> tree = permissionService.getPermissionByParentId(0l);
                model.put("tree", tree);
            }

            model.put("permission", permission);
            model.put("permissionType", permissionService.getPermissionEnums());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return "admin/permissionEdit";
    }

    @RequestMapping("permission/add.html")
    public String add(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        Boolean editParent = false;

        SysPermission permission = new SysPermission();

        try {
            if (StringUtils.isNotBlank(request.getParameter("id")) && StringUtils.isNumeric(request.getParameter("id"))) {
                long id = Long.parseLong(request.getParameter("id"));
                permission = permissionService.selectByPrimaryKey(id);

                if(null != permission) {

                    if(permission.getParentid() == 0) {
                        editParent = true;
                    }
                }
            }


            if(!editParent) {
                List<SysPermission> tree = permissionService.getPermissionByParentId(0l);
                model.put("tree", tree);
            }

            model.put("permission", permission);
            model.put("permissionType", permissionService.getPermissionEnums());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return "admin/permissionEdit";
    }


}
