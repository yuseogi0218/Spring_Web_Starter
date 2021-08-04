package spring_web.starter.controller;

public class MemberForm {
    private String user_name;
    private String user_id;
    private String user_pass;
    private String pass_chk;

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public void setPass_chk(String pass_chk) {
        this.pass_chk = pass_chk;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public String getPass_chk() {
        return pass_chk;
    }

}
