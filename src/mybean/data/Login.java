package mybean.data;

public class Login {
    String account, password, nickname;
    String backNews; //反馈给用户的信息
    boolean success = true; //是否登陆成功
    boolean logined = false; //是否已登陆
}