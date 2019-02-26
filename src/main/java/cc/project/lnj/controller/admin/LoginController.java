package cc.project.lnj.controller.admin;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.servlet.KaptchaExtend;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController {

    @Value("#{config['kaptcha.image.width']}")
    private String kiw;

    @Value("#{config['kaptcha.image.height']}")
    private String kih;

    @RequestMapping("login.html")
    public String login() {

        System.out.println("宽度：" + kiw);
        return "admin/login/login";
    }

    @RequestMapping("captcha.html")
    public void captcha(HttpServletRequest req, HttpServletResponse rep) {

        KaptchaExtend kaptchaExtend = new KaptchaExtend();
        try {
            kaptchaExtend.captcha(req,rep);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
