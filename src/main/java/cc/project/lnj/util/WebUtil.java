package cc.project.lnj.util;


import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebUtil {

    /**
     * 返回JSON
     * @param response
     * @param object
     */
    public static void rendJson(HttpServletResponse response, Object object) {

        try {
            String json = JSONObject.toJSONString(object);
            response.addHeader("Content-type", "application/json");
            ServletOutputStream os = response.getOutputStream();

            try {
                os.write(json.getBytes("utf-8"));
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                os.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 返回Text
     * @param response
     * @param object
     */
    public static void rendText(HttpServletResponse response,Object object){
        try{
            String json = JSONObject.toJSONString(object);
            response.addHeader("Content-type", "text/plain");
            ServletOutputStream os = response.getOutputStream();
            try{
                os.write(json.getBytes());
                os.flush();
            }finally{
                os.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
