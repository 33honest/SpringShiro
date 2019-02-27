package cc.project.lnj.controller.admin;

import cc.project.lnj.domain.RecordResponse;
import cc.project.lnj.service.RoleService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("RoleList.html")
    public String userList(HttpServletRequest req, HttpServletResponse rep, ModelMap map) {



        return "admin/user/roleList";
    }

    @ResponseBody
    @RequestMapping(value = "roleListJson.html", method={RequestMethod.GET},produces="text/html;charset=UTF-8")
    public String getRoleList() {
        List<Object> list = roleService.getList();
        RecordResponse apiResponse = new RecordResponse();
        if(null != apiResponse) {
            apiResponse.setCode(0);
            apiResponse.setCount(list.size());
            apiResponse.setData(list);
            apiResponse.setMsg("");
        }else {

        }
        return JSON.toJSONString(apiResponse);
    }

}
