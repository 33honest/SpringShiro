package cc.project.lnj.enums;

public enum PermissionEnum {

    MENU("menu", "菜单"),ACTION("action", "动作"),PAGE("page", "页面"),BUTTON("button", "按钮");

    private String name;
    private String code;

    private PermissionEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private PermissionEnum(){}

    public String getCode() {
        return this.code;
    }

    public String getName() { return this.name; }

    public static PermissionEnum getPermission(String code) {
        for (PermissionEnum permissionEnum: values()) {
            if(permissionEnum.getCode().equals(code)) {
                return permissionEnum;
            }
        }
        return null;
    }


}