package cc.project.lnj.controller.admin;

import cc.project.lnj.domain.SysUser;
import cc.project.lnj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("userList.html")
    public String userList(HttpServletRequest req, HttpServletResponse rep, ModelMap map) {

        List<SysUser> list = userService.getList();
        map.put("list", list);

        return "admin/userList";
    }

}
