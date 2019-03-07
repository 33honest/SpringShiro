package cc.project.lnj.controller.admin;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Value("#{config['kaptcha.image.width']}")
    private String kiw;


    @RequestMapping(value = "login.html", method = RequestMethod.GET)
    public String login() {


        return "admin/login";
    }

    @RequestMapping(value = "login.html", method = RequestMethod.POST)
    public String checkLogin(HttpServletRequest req, HttpServletResponse rep, ModelMap model) {

        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            return "admin/login";
        }


        return "redirect:/admin/index.html";
    }

    @RequestMapping(value = "logout.html", method = RequestMethod.POST)
    public String logout(HttpServletRequest req, HttpServletResponse rep, ModelMap model) {

        SecurityUtils.getSubject().logout();

        return "admin/login";
    }


}
