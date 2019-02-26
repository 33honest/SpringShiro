package cc.project.lnj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DefaultController {

    @RequestMapping("/index.html")
    @ResponseBody
    public String indexAction() {

        return "Hello WOrld";
    }


}
