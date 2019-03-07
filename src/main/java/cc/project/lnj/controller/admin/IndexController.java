package cc.project.lnj.controller.admin;

import cc.project.lnj.domain.ApiOutPut;
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

    @RequestMapping("error.html")
    public String error(HttpServletRequest req, HttpServletResponse rep, ModelMap map) {

        ApiOutPut outPut = new ApiOutPut("5001");
        outPut.setMsg("访问异常");
        map.put("outPut", outPut);

        return "admin/alert";
    }

}
