package cc.project.lnj.controller.admin;

import cc.project.lnj.domain.ApiOutPut;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseController {

    public String getBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int port = request.getServerPort();
        return scheme + "://" + serverName + ":" + port + path + "/";
    }

    /**
     * 跳转到提示页面
     * @param request
     * @param response
     * @param statusCode
     * @param url
     * @param msg
     */
    public void rendAlter(HttpServletRequest request, HttpServletResponse response, String statusCode, String url, String msg) {

        String basePath = getBasePath(request);

        ApiOutPut outPut = new ApiOutPut("0000");
        if(StringUtils.isBlank(url)) {
            url = "admin/index.html";
        }
        outPut.setUrl(basePath + url);
        if(StringUtils.isNotBlank(msg)) {
            outPut.setMsg(msg);
        }
        if(StringUtils.isNotBlank(statusCode)) {
            outPut.setStatusCode(statusCode);
        }else{
            outPut.setStatusCode("0000");
        }

        request.setAttribute("outPut", outPut);

        try {
            request.getRequestDispatcher("/admin/alert.html").forward(request, response);
            return;
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
