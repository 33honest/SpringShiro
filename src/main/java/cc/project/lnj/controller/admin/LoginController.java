package cc.project.lnj.controller.admin;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
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

        /*
        Subject subject = SecurityUtils.getSubject();
        String userName = req.getParameter("username");
        String password = req.getParameter("password");

        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        try {
            subject.login( token );
            //if no exception, that's it, we're done!
        } catch ( UnknownAccountException uae ) {
            //username wasn't in the system, show them an error message?
        } catch ( IncorrectCredentialsException ice ) {
            //password didn't match, try again?
        } catch ( LockedAccountException lae ) {
            //account for that username is locked - can't login.  Show them a message?
        }
        */

        return "redirect:/admin/index.html";
    }

    @RequestMapping(value = "logout.html", method = RequestMethod.GET)
    public void logout(HttpServletRequest req, HttpServletResponse rep, ModelMap model) {

        SecurityUtils.getSubject().logout();

    }


}
