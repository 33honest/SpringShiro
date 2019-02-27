package cc.project.lnj.controller.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @Value("#{config['kaptcha.image.width']}")
    private String kiw;


    @RequestMapping("login.html")
    public String login() {


        return "admin/login/login";
    }


}
