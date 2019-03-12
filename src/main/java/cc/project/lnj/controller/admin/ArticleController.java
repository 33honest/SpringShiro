package cc.project.lnj.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin/article")
public class ArticleController extends BaseController {

    @RequestMapping("index.html")
    public String userList(HttpServletRequest req, HttpServletResponse rep, ModelMap map) {

        map.put("userName", "时光已逝");

        return "admin/article_index";
    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
    public String add(HttpServletRequest req, HttpServletResponse rep, ModelMap map) {


        return "admin/article_add";
    }

}
