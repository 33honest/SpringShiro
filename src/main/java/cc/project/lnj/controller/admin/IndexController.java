package cc.project.lnj.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class IndexController {

    @RequestMapping("index.html")
    public String userList(HttpServletRequest req, HttpServletResponse rep, ModelMap map) {

        map.put("userName", "时光已逝");

        return "admin/index";
    }

    @RequestMapping("main.html")
    public String main(HttpServletRequest req, HttpServletResponse rep, ModelMap map) {

        return "admin/main";
    }

}
