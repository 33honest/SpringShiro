package cc.project.lnj.controller.admin;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    public String getBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int port = request.getServerPort();
        return scheme + "://" + serverName + ":" + port + path + "/";
    }

}
