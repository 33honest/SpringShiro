package cc.project.lnj.controller.admin;

import cc.project.lnj.ueditor.ActionEnter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@RequestMapping("admin/ue")
@Controller
public class UEditorController extends BaseController {

    @RequestMapping(value="/config.html")
    public void config(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("application/json");
        //String rootPath = request.getSession().getServletContext().getRealPath("/");
        String rootPath = "/upload/images/";
        System.out.println("rootPath:" + rootPath);

        try {
            String exec = new ActionEnter(request, rootPath, "", "").exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
