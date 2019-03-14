package cc.project.lnj.controller.admin;

import cc.project.lnj.domain.SysRole;
import cc.project.lnj.domain.SysUser;
import cc.project.lnj.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin/profile")
public class ProfileController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "edit.html", method = RequestMethod.GET)
    public String add(HttpServletRequest req, HttpServletResponse rep, ModelMap map) {


        try {
            String userName = (String) SecurityUtils.getSubject().getPrincipal();
            SysUser user = userService.getUserByUserCode(userName);
            map.put("user", user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "admin/profileEdit";
    }

    @RequestMapping(value = "edit.html", method = RequestMethod.POST)
    public void update(HttpServletRequest req, HttpServletResponse rep, ModelMap map) {

        try {
            String userCode = (String) SecurityUtils.getSubject().getPrincipal();
            SysUser user = new SysUser();
            user.setId(Integer.valueOf(req.getParameter("id")));
            if (StringUtils.isNotBlank(req.getParameter("username"))) {
                user.setUsername(req.getParameter("username").trim());
            }
            if (StringUtils.isNotBlank(req.getParameter("password"))) {
                user.setPassword(req.getParameter("password").trim());
            }

            if(null != user) {
                userService.update(user);
                rendAlter(req, rep,null,"admin/profile/edit.html","更新成功！");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        rendAlter(req, rep,"4001","admin/profile/edit.html","更新异常！");
    }

}
