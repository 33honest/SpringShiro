package cc.project.lnj.domain;

import java.io.Serializable;

public class ApiOutPut implements Serializable {

    private String statusCode;
    private String msg;
    private Object data;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ApiOutPut() {
    }

    public ApiOutPut(String statusCode, String msg, Object data) {
        this.statusCode = statusCode;
        this.msg = msg;
        this.data = data;
    }

    public ApiOutPut(String statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }

    public ApiOutPut(String statusCode) {
        this.statusCode = statusCode;
    }

    public ApiOutPut(String statusCode, Object data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiOutPut{" +
                "statusCode='" + statusCode + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
