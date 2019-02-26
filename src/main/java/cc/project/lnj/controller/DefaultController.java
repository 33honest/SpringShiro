package cc.project.lnj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class DefaultController {

    @RequestMapping("/index.html")
    @ResponseBody
    public String indexAction() {

        return "Hello WOrld";
    }

    @RequestMapping("/welcome.html")
    public String welcomeAction(HttpServletRequest req, HttpServletResponse rep, ModelMap map) {

        map.put("name", "admin");
        return "welcome";
    }

}
