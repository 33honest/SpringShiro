package cc.project.lnj.controller.admin;

import cc.project.lnj.ueditor.PathFormat;
import cc.project.lnj.ueditor.define.FileType;
import cc.project.lnj.ueditor.upload.StorageManager;
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
                        String savePath = PathFormat.parse("/{yyyy}{mm}{dd}/{time}{rand:6}", file.getOriginalFilename());
                        String suffix = FileType.getSuffixByFilename(file.getOriginalFilename());
                        String newName = savePath + suffix;

                        String targetPath = "/upload/images" + newName;

                        String pathDir = targetPath.substring(0, targetPath.lastIndexOf("/"));
                        File dirFile = new File(pathDir);
                        if(!dirFile.exists()) {
                            dirFile.mkdirs();
                        }
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
