package cc.project.lnj.controller.admin;

import cc.project.lnj.domain.ApiOutPut;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class AlertController extends BaseController {

    @RequestMapping("alert.html")
    public String alert(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        ApiOutPut apiOutPut = (ApiOutPut) request.getAttribute("outPut");
        System.out.println(apiOutPut);

        model.put("result", apiOutPut);
        return "admin/alert";
    }

}
