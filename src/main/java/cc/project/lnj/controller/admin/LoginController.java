package cc.project.lnj.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("login.html")
    public String login() {

        return "admin/login/login";
    }

}
