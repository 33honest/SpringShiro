package cc.project.lnj.controller.admin;

import cc.project.lnj.ueditor.define.FileType;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.codehaus.jackson.map.Serializers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

@RestController
@RequestMapping("/admin/upload")
public class FileController extends Serializers.Base {

    @RequestMapping("image")
    public String image(HttpServletRequest request, HttpServletResponse response) {

        try {

            ShiroHttpServletRequest shiroRequest = (ShiroHttpServletRequest) request;
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
            MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart((HttpServletRequest) shiroRequest.getRequest());




            if (commonsMultipartResolver.isMultipart(request)) {
                //MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart((HttpServletRequest) shiroRequest.getRequest());
                Iterator iterator = multipartRequest.getFileNames();
                while (iterator.hasNext()) {
                    MultipartFile file = multipartRequest.getFile(iterator.next().toString());
                    if(null != file) {
                        String suffix = FileType.getSuffixByFilename(file.getOriginalFilename());
                        String newName = System.currentTimeMillis() + suffix;

                        String targetPath = "/upload/" + newName;
                        System.out.println(targetPath);
                        file.transferTo(new File(targetPath));
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
