package cc.project.lnj.filter;

import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 自定义权限过滤器
 */
public class UrlPermissionsFilter extends PermissionsAuthorizationFilter {

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        return super.isAccessAllowed(request, response, buildPermissionsFromRequest(request));
    }

    protected String[] buildPermissionsFromRequest(ServletRequest request) {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String uri = servletRequest.getRequestURI();
        String tmpUri = uri.substring(1, uri.length());
        String reUri = tmpUri.substring(tmpUri.indexOf("/"), tmpUri.length());
        return new String[]{reUri};//返回请求URI
    }

}
